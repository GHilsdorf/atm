package gc.hilsdorf.atm;

import static org.assertj.core.api.Assertions.assertThat;
import gc.hilsdorf.atm.Controller.UnitController;
import gc.hilsdorf.atm.model.BalanceRequest;
import gc.hilsdorf.atm.model.BalanceResponse;
import gc.hilsdorf.atm.model.User;
import gc.hilsdorf.atm.model.WithdrawRequest;
import gc.hilsdorf.atm.model.WithdrawResponse;
import gc.hilsdorf.atm.repository.UserRepository;
import gc.hilsdorf.atm.service.UnitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

/**
 * This class tests the scenarios used inside the UnitController class
 */
@ExtendWith(MockitoExtension.class)
public class UnitControllerTest {

    @InjectMocks
    UnitController controller;

    @Mock
    UserRepository userRepository;

    @Mock
    UnitService unitService;

    /**
     * Tests a successful balance request.
     *
     * @throws Exception if test fails
     */
    @Test
    public void successfulBalanceTest() throws Exception {

        //Mocked values
        User mockUser = new User(123456789, 1234, 800, 200);
        when(userRepository.findInformationIfValid(123456789, 1234)).thenReturn(mockUser);

        //Expected results
        BalanceResponse expectedResponse = new BalanceResponse(800, 1000);

        //Request execution
        BalanceRequest req = new BalanceRequest(123456789, 1234);
        ResponseEntity<BalanceResponse> obtainedResponse = controller.balance(req);

        //Test execution
        assertThat(obtainedResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(obtainedResponse.getBody()).isEqualTo(expectedResponse);
    }

    /**
     * Tests a balance request using an invalid pin/account combination.
     *
     * @throws Exception if test fails
     */
    @Test
    public void wrongPinBalanceTest() throws Exception {

        //Mocked values
        User mockUser = null;
        when(userRepository.findInformationIfValid(123456789, 1234)).thenReturn(mockUser);

        //Request execution
        BalanceRequest req = new BalanceRequest(123456789, 1234);
        ResponseEntity<BalanceResponse> obtainedResponse = controller.balance(req);

        //Test execution
        assertThat(obtainedResponse.getStatusCodeValue()).isEqualTo(401);
    }

    /**
     * Tests a successful withdraw request.
     *
     * @throws Exception if test fails
     */
    @Test
    public void successfulWithdrawTest() throws Exception {

        //Mocked values
        User mockUser = new User(123456789, 1234, 800, 200);
        WithdrawResponse mockWithdraw = new WithdrawResponse(600, "Withdraw notes: 4x50.00, 0x20.00, 0x10.00, 0x5.00");
        when(userRepository.findInformationIfValid(123456789, 1234)).thenReturn(mockUser);
        when(unitService.withdraw(mockUser, 200, 1)).thenReturn(mockWithdraw);

        //Expected results
        WithdrawResponse expectedResponse = new WithdrawResponse(600, "Withdraw notes: 4x50.00, 0x20.00, 0x10.00, 0x5.00");

        //Request execution
        WithdrawRequest req = new WithdrawRequest(123456789, 1234, 200, 1);
        ResponseEntity<WithdrawResponse> obtainedResponse = controller.withdraw(req);

        //Test execution
        assertThat(obtainedResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(obtainedResponse.getBody()).isEqualTo(expectedResponse);
    }

    /**
     * Tests a withdraw request using an invalid pin/account combination.
     *
     * @throws Exception if test fails
     */
    @Test
    public void wrongPinWithdrawTest() throws Exception {

        //Mocked values
        User mockUser = null;
        when(userRepository.findInformationIfValid(123456789, 1234)).thenReturn(mockUser);

        //Request execution
        WithdrawRequest req = new WithdrawRequest(123456789, 1234, 200, 1);
        ResponseEntity<WithdrawResponse> obtainedResponse = controller.withdraw(req);

        //Test execution
        assertThat(obtainedResponse.getStatusCodeValue()).isEqualTo(401);
    }

    /**
     * Tests a withdraw request invalid where the response should have the code
     * 403 FORBIDDEN.
     *
     * @throws Exception if test fails
     */
    @Test
    public void errorWithdrawTest() throws Exception {

        //Mocked values
        User mockUser = new User(123456789, 1234, 800, 200);
        WithdrawResponse mockWithdraw = new WithdrawResponse(0, "ERROR: Invalid withdraw value.");
        when(userRepository.findInformationIfValid(123456789, 1234)).thenReturn(mockUser);
        when(unitService.withdraw(mockUser, 200, 1)).thenReturn(mockWithdraw);

        //Expected results
        WithdrawResponse expectedResponse = new WithdrawResponse(0, "ERROR: Invalid withdraw value.");

        //Request execution
        WithdrawRequest req = new WithdrawRequest(123456789, 1234, 200, 1);
        ResponseEntity<WithdrawResponse> obtainedResponse = controller.withdraw(req);

        //Test execution
        assertThat(obtainedResponse.getStatusCodeValue()).isEqualTo(403);
        assertThat(obtainedResponse.getBody()).isEqualTo(expectedResponse);
    }
}
