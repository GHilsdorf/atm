package gc.hilsdorf.atm.Controller;

import gc.hilsdorf.atm.model.BalanceRequest;
import gc.hilsdorf.atm.model.BalanceResponse;
import gc.hilsdorf.atm.model.User;
import gc.hilsdorf.atm.model.WithdrawRequest;
import gc.hilsdorf.atm.model.WithdrawResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import gc.hilsdorf.atm.service.UnitService;
import gc.hilsdorf.atm.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller responsible for the ATM unit transactions.
 */
@RestController
public class UnitController {

    @Autowired
    private UnitService unitService;

    @Autowired
    private UserRepository userService;

    /**
     * Process the user information based on the account number and if the
     * information is valid, the balance value is returned.
     *
     * @param req should contain the account number and corresponding pin.
     * @return account balance, note that the value can be negative.
     */
    @PostMapping("/balance")
    public ResponseEntity<BalanceResponse> balance(@RequestBody BalanceRequest req) {

        try {
            User user = findInformationIfValid(req.getAccountNumber(), req.getPin());
            if (null != user) {
                BalanceResponse resp = new BalanceResponse(user.getBalance(), user.getBalance() + user.getOverdraft());
                return new ResponseEntity<>(resp, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Process the user information based on the account number and if the
     * account information and withdraw value are valid, the small amount of
     * notes as possible are returned and the values are deduced from the ATM
     * unit and user account balance.
     *
     * @param req should contain the unit id in order to verify the available
     * notes, the withdraw value and account number and corresponding pin.
     * @return the remaining balance in the user account and the notes to be
     * withdraw.
     */
    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(@RequestBody WithdrawRequest req) {

        try {
            User user = findInformationIfValid(req.getAccountNumber(), req.getPin());
            if (null != user) {
                WithdrawResponse resp = unitService.withdraw(user, req.getWithdrawValue(), req.getUnitId());
                if (resp.getWithdrawNotes().substring(0, 5).equals("ERROR")) {
                    return new ResponseEntity<>(resp, HttpStatus.FORBIDDEN);
                }
                return new ResponseEntity<>(resp, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Validate if the user account and pin informed match and if they do
     * returns the user information.
     *
     * @param accountNumber
     * @param pin
     * @return null if the account/pin combination is invalid, returns the user
     * information if they are valid.
     */
    private User findInformationIfValid(int accountNumber, int pin) {
        return userService.findInformationIfValid(accountNumber, pin);
    }
}
