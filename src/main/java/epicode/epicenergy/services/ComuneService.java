package epicode.epicenergy.services;

import epicode.epicenergy.entities.Comune;
import epicode.epicenergy.repositories.ComuneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComuneService {
    private final ComuneImportService importer;
    private final ComuneRepository comuneRepository;

    public ComuneService(ComuneImportService importer, ComuneRepository comuneRepository) {
        this.importer = importer;
        this.comuneRepository = comuneRepository;
    }

    public void importa() {
        List<Comune> comuni = importer.load("comuni-italiani.csv");
        comuneRepository.saveAll(comuni);
        System.out.println("Importati: " + comuni.size());
    }
}
