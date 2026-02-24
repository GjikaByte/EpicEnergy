package epicode.epicenergy.services;

import epicode.epicenergy.entities.Provincia;
import epicode.epicenergy.repositories.ProvinceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProvinceService {
    private final ProvinceImportService importer;
    private final ProvinceRepository provinceRepository;

    public ProvinceService(ProvinceImportService importer, ProvinceRepository provinceRepository) {
        this.importer = importer;
        this.provinceRepository = provinceRepository;
    }

    public void importa() {
        List<Provincia> province = importer.load("province-italiane.csv");
        provinceRepository.saveAll(province);
        System.out.println("Importati: " + province.size());
    }
}
