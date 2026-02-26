package epicode.epicenergy.repositories;

import epicode.epicenergy.entities.Stato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatoRepository extends JpaRepository<Stato, UUID> {
    Optional<Stato> findByStato(String stato);
}
