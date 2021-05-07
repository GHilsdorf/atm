package gc.hilsdorf.atm.repository;

import gc.hilsdorf.atm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Populate the user based on the match of informed account number and pin.
     *
     * @param accountNumber represents the unique id of the user in the
     * database(in a real scenario these two values should be separated).
     * @param pin a valid pin to the informed account.
     * @return Populated User object if the informed account and pin are valid,
     * otherwise a null will be returned.
     */
    @Query("SELECT new gc.hilsdorf.atm.model.User(u.id, u.pin, u.balance, u.overdraft) FROM User u WHERE u.id = ?1 AND u.pin = ?2")
    User findInformationIfValid(int accountNumber, int pin);
}
