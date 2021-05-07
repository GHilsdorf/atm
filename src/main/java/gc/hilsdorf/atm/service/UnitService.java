package gc.hilsdorf.atm.service;

import gc.hilsdorf.atm.model.User;
import gc.hilsdorf.atm.model.WithdrawResponse;

public interface UnitService{

    public WithdrawResponse withdraw(User user, int withdrawValue, int unitId);
}
