package epicode.epicenergy.controllers;

import epicode.epicenergy.services.ComuneService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/comuni")
public class ComuniController {
    private final ComuneService comuneService;

    public ComuniController(ComuneService comuneService) {
        this.comuneService = comuneService;
    }

    @PostMapping("/importa")
    public String importa() {
        comuneService.importa();
        return "Import completato";
    }

}
