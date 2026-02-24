package epicode.epicenergy.repositories;

import epicode.epicenergy.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Provincia, String> {
}
