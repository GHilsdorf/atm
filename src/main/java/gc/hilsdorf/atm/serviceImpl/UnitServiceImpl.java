package gc.hilsdorf.atm.serviceImpl;

import gc.hilsdorf.atm.model.Unit;
import gc.hilsdorf.atm.model.User;
import gc.hilsdorf.atm.model.WithdrawResponse;
import gc.hilsdorf.atm.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gc.hilsdorf.atm.repository.UnitRepository;
import gc.hilsdorf.atm.repository.UserRepository;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * If all the provided information is valid, the withdraw value is removed
     * from the user account and the notes are removed from the ATM unit and
     * send in the response.
     *
     * @param user valid user information.
     * @param withdrawValue bigger than zero and divisible by five value to be
     * withdraw.
     * @param unitId valid ATM unit id.
     * @return the remaining balance in the user account and the notes to be
     * withdraw.
     */
    @Override
    public WithdrawResponse withdraw(User user, int withdrawValue, int unitId) {

        Unit unit = unitRepository.findUnitById(unitId);

        // <editor-fold defaultstate="collapsed" desc=" Validations ">
        if (withdrawValue <= 0 || withdrawValue % 5 != 0) {
            return new WithdrawResponse(0, "ERROR: Invalid withdraw value.");
        }
        if (null == unit) {
            return new WithdrawResponse(0, "ERROR: ATM machine not indentified.");
        } else if (unit.getTotalCash() < withdrawValue) {
            return new WithdrawResponse(0, "ERROR: Max value to withdraw is: "
                    + unit.getTotalCash() + ".00. Sorry for the inconvenience.");
        }
        if (user.getBalance() + user.getOverdraft() < withdrawValue) {
            return new WithdrawResponse(0, "ERROR: Insufficient funds.");
        }
        //</editor-fold>

        user.setBalance(user.getBalance() - withdrawValue);
        unit.withdraw(withdrawValue);

        userRepository.save(user);
        unitRepository.save(unit);

        return new WithdrawResponse(user.getBalance(), unit.getLastWithdraw());
    }

}
