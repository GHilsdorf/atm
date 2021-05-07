package gc.hilsdorf.atm.repository;

import gc.hilsdorf.atm.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    /**
     * Populate the unit based on the informed id.
     *
     * @param unitId valid unit id.
     * @return Populated Unit object if the informed id is valid, otherwise a
     * null will be returned.
     */
    @Query("SELECT new gc.hilsdorf.atm.model.Unit(u.id, u.fiftyNotes, u.twentyNotes, u.tenNotes, u.fiveNotes) FROM Unit u WHERE u.id = ?1")
    Unit findUnitById(int unitId);
}
