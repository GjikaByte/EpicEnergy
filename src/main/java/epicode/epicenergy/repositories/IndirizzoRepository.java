package epicode.epicenergy.repositories;

import epicode.epicenergy.entities.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndirizzoRepository extends JpaRepository<Indirizzo, UUID> {
    @Query("SELECT i FROM Indirizzo i WHERE i.via = :via AND i.civico = :civico AND i.localita= :localita")
    Optional<Indirizzo> findEventoByViaAndCivicoAndLocalita(
            @Param("via") String via,
            @Param("civico") int civico,
            @Param("localita") String localita
    );
}
