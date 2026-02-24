package epicode.epicenergy.controllers;

import epicode.epicenergy.DTOs.IndirizzoDTO;
import epicode.epicenergy.entities.Indirizzo;
import epicode.epicenergy.services.IndirizzoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("indirizzi")
public class IndirizzoController {

    private final IndirizzoService indirizzoService;

    @Autowired
    public IndirizzoController(IndirizzoService indirizzoService ){
        this.indirizzoService = indirizzoService;
    }

    // 1. POST http://localhost:3001/indirizzi (+ Payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Indirizzo createUser(@RequestBody @Valid IndirizzoDTO payload) {
        return this.indirizzoService.save(payload);
    }

    // 2. GET http://localhost:3001/indirizzi
    @GetMapping
    public Page<Indirizzo> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "comune") String orderBy,
                                    @RequestParam(defaultValue = "asc") String sortCriteria) {

        return this.indirizzoService.findAll(page, size, orderBy, sortCriteria);
    }

    // 3. GET http://localhost:3001/indirizzi/{indirizzoId}
    @GetMapping("/{indirizzoId}")
    public Indirizzo findById(@PathVariable UUID indirizzoId) {
        return this.indirizzoService.findById(indirizzoId);
    }


    // 4. DELETE http://localhost:3001/indirizzi/{indirizzoId}
    @DeleteMapping("/{indirizzoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID indirizzoId) {
        this.indirizzoService.findByIdAndDelete(indirizzoId);
    }

    // 5. PUT http://localhost:3001/indirizzi/{indirizzoId}
    @PutMapping("/{indirizzoId}")
    public Indirizzo findByIdAndUpdate(@PathVariable UUID indirizzoId, @RequestBody IndirizzoDTO payload) {
        return this.indirizzoService.findByIdAndUpdate(indirizzoId, payload);
    }
}
