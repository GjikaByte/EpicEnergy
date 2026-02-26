package epicode.epicenergy.controllers;

import epicode.epicenergy.DTOs.FatturaDTO;
import epicode.epicenergy.entities.Fattura;
import epicode.epicenergy.exceptions.NotFoundException;
import epicode.epicenergy.services.FatturaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fatture")
public class FatturaController {

    private final FatturaService fatturaService;

    public FatturaController(FatturaService fatturaService) {
        this.fatturaService = fatturaService;
    }

    @PostMapping
    public Fattura salvaFattura(@RequestBody FatturaDTO fatturaDTO) {
        return fatturaService.save(fatturaDTO);
    }

    @GetMapping
    public List<Fattura> getFatture() {
        return fatturaService.findAll();
    }

    @GetMapping("/{id}")
    public Fattura getFatturaById(@PathVariable UUID id) {
        Fattura fattura = fatturaService.trovaPerId(id);
        if (fattura == null) {
            throw new NotFoundException("Fattura non trovata");
        }
        return fattura;
    }

    @DeleteMapping("/{id}")
    public void eliminaFattura(@PathVariable UUID id) {
        fatturaService.eliminaFattura(id);
    }
}
