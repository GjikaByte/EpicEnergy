package epicode.epicenergy.controllers;

import epicode.epicenergy.DTOs.RuoloDTO;
import epicode.epicenergy.entities.Ruolo;
import epicode.epicenergy.exceptions.ValidationException;
import epicode.epicenergy.services.RuoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ruoli")
public class RuoloController {

    private final RuoloService ruoloService;

    @Autowired
    public RuoloController(RuoloService ruoloService){
        this.ruoloService = ruoloService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ruolo salvaRuolo(@RequestBody @Validated RuoloDTO payload, BindingResult validationResult){
        if(validationResult.hasErrors()){
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.ruoloService.salvaRuolo(payload);
        }
    }

    @DeleteMapping("/{ruoloId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRuolo(@PathVariable UUID ruoloId) {
        this.ruoloService.findByIdAndDelete(ruoloId);
    }

}