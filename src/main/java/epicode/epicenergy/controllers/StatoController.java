package epicode.epicenergy.controllers;

import epicode.epicenergy.DTOs.RuoloDTO;
import epicode.epicenergy.DTOs.StatoDTO;
import epicode.epicenergy.entities.Ruolo;
import epicode.epicenergy.entities.Stato;
import epicode.epicenergy.exceptions.ValidationException;
import epicode.epicenergy.repositories.StatoRepository;
import epicode.epicenergy.services.RuoloService;
import epicode.epicenergy.services.StatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stati")
public class StatoController {

    private final StatoService statoService;
    private final StatoRepository statoRepository;

    @Autowired
    public StatoController(StatoService statoService,StatoRepository statoRepository) {
        this.statoService = statoService;
        this.statoRepository = statoRepository;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Stato salvaStato(@RequestBody @Validated StatoDTO payload, BindingResult validationResult){
        if(validationResult.hasErrors()){
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.statoService.salvaStato(payload);
        }
    }

    @DeleteMapping("/{statoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStato(@PathVariable UUID statoId) {
        this.statoService.findByIdAndDelete(statoId);
    }

}