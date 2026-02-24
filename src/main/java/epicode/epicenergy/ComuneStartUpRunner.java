package epicode.epicenergy;

import epicode.epicenergy.services.ComuneService;
import epicode.epicenergy.services.ProvinceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ComuneStartUpRunner implements CommandLineRunner {

    private final ComuneService comuneService;
    private final ProvinceService provinceService;

    public ComuneStartUpRunner(ComuneService comuneService,ProvinceService provinceService) {
        this.comuneService = comuneService;
        this.provinceService = provinceService;

    }

    @Override
    public void run(String... args) {
        comuneService.importa();
        provinceService.importa();
    }
}