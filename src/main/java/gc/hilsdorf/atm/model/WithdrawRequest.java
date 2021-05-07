package gc.hilsdorf.atm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawRequest {

    private int accountNumber;
    private int pin;
    private int withdrawValue;
    private int unitId;
}
