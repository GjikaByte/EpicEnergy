package epicode.epicenergy.repositories;

import epicode.epicenergy.entities.Ruolo;
import epicode.epicenergy.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RuoloRepository extends JpaRepository<Ruolo, UUID> {
    Optional<Ruolo> findByRuolo(String ruolo);
}
