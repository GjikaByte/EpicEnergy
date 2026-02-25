package epicode.epicenergy.repositories;

import epicode.epicenergy.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Provincia, String> {
    Optional<Provincia> findByProvinciaIgnoreCase(String provincia);
}
