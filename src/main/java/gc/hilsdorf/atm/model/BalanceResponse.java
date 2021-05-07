package gc.hilsdorf.atm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceResponse {
    private int balance;
    private int maxWithdrawAmount;
}
