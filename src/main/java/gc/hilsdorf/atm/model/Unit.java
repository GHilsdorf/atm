package gc.hilsdorf.atm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/**
 * Unit refers to the ATM machine unit, this is not called ATM to avoid
 * ambiguities with the project name that is ATM, the project has this name
 * because is responsible for managing all the ATM transactions that includes
 * user account interactions and manage each ATM unit.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "unit")
public class Unit {

    public Unit(int id, int fiftyNotes, int twenty_notes, int ten_notes, int fiveNotes) {
        this.id = id;
        this.fiftyNotes = fiftyNotes;
        this.twentyNotes = twenty_notes;
        this.tenNotes = ten_notes;
        this.fiveNotes = fiveNotes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "fifty_notes")
    private int fiftyNotes;
    @Column(name = "twenty_notes")
    private int twentyNotes;
    @Column(name = "ten_notes")
    private int tenNotes;
    @Column(name = "five_notes")
    private int fiveNotes;
    @Transient
    private String lastWithdraw;

    /**
     * Display the total amount of cash based on the notes available.
     *
     * @return an int value with the total cash value.
     */
    public int getTotalCash() {
        return fiftyNotes * 50
                + twentyNotes * 20
                + tenNotes * 10
                + fiveNotes * 5;
    }

    /**
     * Find the small amount of notes based on the provided value, remove those
     * notes from the unit and save it in the lastWithdraw.
     *
     * @param value is the amount to be withdraw from the machine.
     */
    public void withdraw(int value) {
        int withdraw50Notes = 0;
        int withdraw20Notes = 0;
        int withdraw10Notes = 0;
        int withdraw5Notes = 0;

        while (value > 0) {
            if (value >= 50 && fiftyNotes > 0) {
                value = value - 50;
                fiftyNotes--;
                withdraw50Notes++;
            } else if (value >= 20 && twentyNotes > 0) {
                value = value - 20;
                twentyNotes--;
                withdraw20Notes++;
            } else if (value >= 10 && tenNotes > 0) {
                value = value - 10;
                tenNotes--;
                withdraw10Notes++;
            } else if (value >= 5 && fiveNotes > 0) {
                value = value - 5;
                fiveNotes--;
                withdraw5Notes++;
            }
        }
        lastWithdraw = "Withdraw notes: " + withdraw50Notes + "x50.00, "
                + withdraw20Notes + "x20.00, "
                + withdraw10Notes + "x10.00, "
                + withdraw5Notes + "x5.00";
    }
}
