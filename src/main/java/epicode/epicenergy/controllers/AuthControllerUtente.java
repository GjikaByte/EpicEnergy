package epicode.epicenergy.controllers;

import epicode.epicenergy.DTOs.*;
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

@RestController
@RequestMapping("/auth")
public class AuthControllerUtente {
    private final AuthServiceUtente authService;
    private final UtentiService utenteService;

    @Autowired
    public AuthControllerUtente(AuthServiceUtente authService, UtentiService utenteService) {
        this.authService = authService;
        this.utenteService = utenteService;
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
}
