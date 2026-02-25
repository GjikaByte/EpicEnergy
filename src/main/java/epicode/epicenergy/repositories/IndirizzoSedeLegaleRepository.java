package epicode.epicenergy.repositories;

import epicode.epicenergy.entities.IndirizzoSedeLegale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndirizzoSedeLegaleRepository extends JpaRepository<IndirizzoSedeLegale, UUID> {
    @Query("SELECT i FROM IndirizzoSedeLegale i WHERE i.via = :via AND i.civico = :civico AND i.localita= :localita")
    Optional<IndirizzoSedeLegale> findEventoByViaAndCivicoAndLocalita(
            @Param("via") String via,
            @Param("civico") int civico,
            @Param("localita") String localita
    );

}

