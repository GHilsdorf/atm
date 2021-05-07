package gc.hilsdorf.atm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    private int accountNumber;
    @Column(name = "pin")
    private int pin;
    @Column(name = "balance")
    private int balance;
    @Column(name = "overdraft")
    private int overdraft;
}
