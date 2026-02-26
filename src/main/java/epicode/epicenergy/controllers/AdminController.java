package epicode.epicenergy.controllers;

import epicode.epicenergy.DTOs.IndirizzoDTO;
import epicode.epicenergy.DTOs.UtenteDTO;
import epicode.epicenergy.entities.Utente;
import epicode.epicenergy.services.UtentiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final UtentiService utenteService;

    @Autowired
    public AdminController(UtentiService utenteService ){
        this.utenteService = utenteService;
    }

    // 1. POST http://localhost:3001/admin (+ Payload)
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente createUser(@RequestBody @Valid UtenteDTO payload) {
        return this.utenteService.save(payload);
    }

    // GET http://localhost:3001/admin
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<Utente> findAll() {
        return this.utenteService.findAllNoPagination();
    }

    // 3. GET http://localhost:3001/admin/{utenteId}
    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Utente findById(@PathVariable UUID utenteId) {
        return this.utenteService.findById(utenteId);
    }


    // 4. DELETE http://localhost:3001/admin/{utenteId}
    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID utenteId) {
        this.utenteService.findByIdAndDelete(utenteId);
    }

    // 5. PUT http://localhost:3001/admin/{utenteId}
    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Utente findByIdAndUpdate(@PathVariable UUID utenteId, @RequestBody UtenteDTO payload) {
        return this.utenteService.findByIdAndUpdate(utenteId, payload);
    }
}
