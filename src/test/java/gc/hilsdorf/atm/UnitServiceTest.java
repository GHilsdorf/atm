package gc.hilsdorf.atm;

import gc.hilsdorf.atm.model.Unit;
import gc.hilsdorf.atm.model.User;
import gc.hilsdorf.atm.model.WithdrawResponse;
import gc.hilsdorf.atm.repository.UnitRepository;
import gc.hilsdorf.atm.repository.UserRepository;
import gc.hilsdorf.atm.serviceImpl.UnitServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * This class tests the scenarios used inside the unitService class
 */
@ExtendWith(MockitoExtension.class)
public class UnitServiceTest {

    @InjectMocks
    UnitServiceImpl unitService;

    @Mock
    UserRepository userRepository;

    @Mock
    UnitRepository unitRepository;

    /**
     * Tests a successful unitService.withdraw().
     *
     * @throws Exception if test fails
     */
    @Test
    public void successfulWithdrawTest() throws Exception {

        //Mocked values
        Unit mockUnit = new Unit(1, 10, 30, 30, 20);
        when(unitRepository.findUnitById(1)).thenReturn(mockUnit);
        User mockUser = new User(123456789, 1234, 1501, 200);

        //Expected results
        WithdrawResponse expectedResponse = new WithdrawResponse(1, "Withdraw notes: 10x50.00, 30x20.00, 30x10.00, 20x5.00");

        //Execution
        WithdrawResponse obtainedResponse = unitService.withdraw(mockUser, 1500, 1);

        //Test execution
        assertThat(obtainedResponse).isEqualTo(expectedResponse);
    }

    /**
     * Tests a successful unitService.withdraw() using the overdraft limit.
     *
     * @throws Exception if test fails
     */
    @Test
    public void successfulWithdrawUsingOverdraftTest() throws Exception {

        //Mocked values
        Unit mockUnit = new Unit(1, 10, 30, 30, 20);
        when(unitRepository.findUnitById(1)).thenReturn(mockUnit);
        User mockUser = new User(123456789, 1234, 1300, 200);

        //Expected results
        WithdrawResponse expectedResponse = new WithdrawResponse(-200, "Withdraw notes: 10x50.00, 30x20.00, 30x10.00, 20x5.00");

        //Execution
        WithdrawResponse obtainedResponse = unitService.withdraw(mockUser, 1500, 1);

        //Test execution
        assertThat(obtainedResponse).isEqualTo(expectedResponse);
    }

    /**
     * Tests unitService.withdraw() when a value is not divisible by 5, in this
     * case the response should inform that the value is invalid.
     *
     * @throws Exception if test fails
     */
    @Test
    public void withdrawInvalidValueTest() throws Exception {

        //Mocked values
        Unit mockUnit = new Unit(1, 10, 30, 30, 20);
        when(unitRepository.findUnitById(1)).thenReturn(mockUnit);
        User mockUser = new User(123456789, 1234, 1300, 200);

        //Expected results
        WithdrawResponse expectedResponse = new WithdrawResponse(0, "ERROR: Invalid withdraw value.");

        //Execution
        WithdrawResponse obtainedResponse = unitService.withdraw(mockUser, 1501, 1);

        //Test execution
        assertThat(obtainedResponse).isEqualTo(expectedResponse);
    }

    /**
     * Tests unitService.withdraw() when the withdraw value is bigger than the
     * amount of cash available in the ATM unit.
     *
     * @throws Exception if test fails
     */
    @Test
    public void withdrawExceedMaxValueTest() throws Exception {

        //Mocked values
        Unit mockUnit = new Unit(1, 10, 30, 30, 20);
        when(unitRepository.findUnitById(1)).thenReturn(mockUnit);
        User mockUser = new User(123456789, 1234, 1300, 200);

        //Expected results
        WithdrawResponse expectedResponse = new WithdrawResponse(0, "ERROR: Max value to withdraw is: 1500.00. Sorry for the inconvenience.");

        //Execution
        WithdrawResponse obtainedResponse = unitService.withdraw(mockUser, 1505, 1);

        //Test execution
        assertThat(obtainedResponse).isEqualTo(expectedResponse);
    }

    /**
     * Tests unitService.withdraw() when the withdraw value is bigger than the
     * account withdraw limit.
     *
     * @throws Exception if test fails
     */
    @Test
    public void withdrawExceedAccountLimitTest() throws Exception {

        //Mocked values
        Unit mockUnit = new Unit(1, 10, 30, 30, 20);
        when(unitRepository.findUnitById(1)).thenReturn(mockUnit);
        User mockUser = new User(123456789, 1234, 1299, 200);

        //Expected results
        WithdrawResponse expectedResponse = new WithdrawResponse(0, "ERROR: Insufficient funds.");

        //Execution
        WithdrawResponse obtainedResponse = unitService.withdraw(mockUser, 1500, 1);

        //Test execution
        assertThat(obtainedResponse).isEqualTo(expectedResponse);
    }
}
