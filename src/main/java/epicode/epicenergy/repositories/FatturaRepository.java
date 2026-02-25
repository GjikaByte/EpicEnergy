package epicode.epicenergy.repositories;

import epicode.epicenergy.entities.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura,UUID>, JpaSpecificationExecutor<Fattura> {
    Optional<Fattura> findByNumeroFattura(Long numeroFattura);
    boolean existsByNumeroFattura(Long numeroFattura);
}
