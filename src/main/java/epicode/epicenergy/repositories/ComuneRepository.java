package epicode.epicenergy.repositories;

import epicode.epicenergy.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComuneRepository extends JpaRepository<Comune, String> {
}
