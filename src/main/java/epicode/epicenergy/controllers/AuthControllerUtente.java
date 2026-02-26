package epicode.epicenergy.controllers;

import epicode.epicenergy.DTOs.*;
import epicode.epicenergy.entities.Ruolo;
import epicode.epicenergy.entities.Utente;
import epicode.epicenergy.exceptions.ValidationException;
import epicode.epicenergy.services.*;
import epicode.epicenergy.services.AuthServiceUtente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthControllerUtente {
    private final AuthServiceUtente authService;
    private final UtentiService utenteService;
    private final RuoloService ruoloService;

    @Autowired
    public AuthControllerUtente(AuthServiceUtente authService, UtentiService utenteService, RuoloService ruoloService) {
        this.authService = authService;
        this.utenteService = utenteService;
        this.ruoloService = ruoloService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {

        return new LoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente createUtente(@RequestBody @Validated UtenteDTO payload, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.utenteService.save(payload);
        }
    }
    @PostMapping("/registerAdmin")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente createAdmin(@RequestBody @Validated UtenteDTO payload, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.utenteService.saveAdmin(payload);

        }
    }

    @PostMapping("/ruoli")
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

    @DeleteMapping("/ruoli/{ruoloId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRuolo(@PathVariable UUID ruoloId) {
        this.ruoloService.findByIdAndDelete(ruoloId);
    }
}
