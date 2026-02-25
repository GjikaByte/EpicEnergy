package epicode.epicenergy.controllers;

import epicode.epicenergy.DTOs.IndirizzoDTO;
import epicode.epicenergy.entities.IndirizzoSedeLegale;
import epicode.epicenergy.entities.IndirizzoSedeOperativa;
import epicode.epicenergy.services.IndirizzoSedeLegaleService;
import epicode.epicenergy.services.IndirizzoSedeOperativaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class IndirizzoController {

    private final IndirizzoSedeLegaleService indirizzoSedeLegaleService;
    private final IndirizzoSedeOperativaService indirizzoSedeOperativaService;

    @Autowired
    public IndirizzoController(IndirizzoSedeLegaleService indirizzoSedeLegaleService, IndirizzoSedeOperativaService indirizzoSedeOperativaService ){
        this.indirizzoSedeLegaleService = indirizzoSedeLegaleService;
        this.indirizzoSedeOperativaService = indirizzoSedeOperativaService;
    }

    // 1.1 POST http://localhost:3001/indirizziSedeLegale (+ Payload)
    @PostMapping("/indirizziSedeLegale")
    @ResponseStatus(HttpStatus.CREATED)
    public IndirizzoSedeLegale createIndirizzoSedeLegale(@RequestBody @Valid IndirizzoDTO payload) {
        return this.indirizzoSedeLegaleService.save(payload);
    }

    // 1.2 POST http://localhost:3001/indirizziSedeOperativa (+ Payload)
    @PostMapping("/indirizziSedeOperativa")
    @ResponseStatus(HttpStatus.CREATED)
    public IndirizzoSedeOperativa createIndirizzoSedeOperativa(@RequestBody @Valid IndirizzoDTO payload) {
        return this.indirizzoSedeOperativaService.save(payload);
    }

    // 2.1 GET http://localhost:3001/indirizziSedeLegale
    @GetMapping("/indirizziSedeLegale")
    public Page<IndirizzoSedeLegale> findAllIndirizziSedeLegale(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "comune") String orderBy,
                                             @RequestParam(defaultValue = "asc") String sortCriteria) {

        return this.indirizzoSedeLegaleService.findAll(page, size, orderBy, sortCriteria);
    }

    // 2.2 GET http://localhost:3001/indirizziSedeOperativa
    @GetMapping("/indirizziSedeOperativa")
    public Page<IndirizzoSedeOperativa> findAllIndirizziSedeOperativa(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "comune") String orderBy,
                                             @RequestParam(defaultValue = "asc") String sortCriteria) {

        return this.indirizzoSedeOperativaService.findAll(page, size, orderBy, sortCriteria);
    }

    // 3.1 GET http://localhost:3001/indirizziSedeLegale/{indirizzoId}
    @GetMapping("/indirizziSedeLegale/{indirizzoId}")
    public IndirizzoSedeLegale findByIdLegale(@PathVariable UUID indirizzoId) {
        return this.indirizzoSedeLegaleService.findById(indirizzoId);
    }

    // 3.2 GET http://localhost:3001/indirizziSedeOperativa/{indirizzoId}
    @GetMapping("/indirizziSedeOperativa/{indirizzoId}")
    public IndirizzoSedeOperativa findByIdOperativa(@PathVariable UUID indirizzoId) {
        return this.indirizzoSedeOperativaService.findById(indirizzoId);
    }


    // 4.1 DELETE http://localhost:3001/indirizziSedeLegale/{indirizzoId}
    @DeleteMapping("/indirizziSedeLegale/{indirizzoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDeleteLegale(@PathVariable UUID indirizzoId) {
        this.indirizzoSedeLegaleService.findByIdAndDelete(indirizzoId);
    }

    // 4.2 DELETE http://localhost:3001/indirizzi/{indirizzoId}
    @DeleteMapping("/indirizziSedeOperativa/{indirizzoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDeleteOperativa(@PathVariable UUID indirizzoId) {
        this.indirizzoSedeOperativaService.findByIdAndDelete(indirizzoId);
    }

    // 5.1 PUT http://localhost:3001/indirizziSedeLegale/{indirizzoId}
    @PutMapping("/indirizziSedeLegale/{indirizzoId}")
    public IndirizzoSedeLegale findByIdAndUpdateLegale(@PathVariable UUID indirizzoId, @RequestBody IndirizzoDTO payload) {
        return this.indirizzoSedeLegaleService.findByIdAndUpdate(indirizzoId, payload);
    }

    // 5.2 PUT http://localhost:3001/indirizziSedeOperativa/{indirizzoId}
    @PutMapping("/indirizziSedeOperativa/{indirizzoId}")
    public IndirizzoSedeOperativa findByIdAndUpdateOperativa(@PathVariable UUID indirizzoId, @RequestBody IndirizzoDTO payload) {
        return this.indirizzoSedeOperativaService.findByIdAndUpdate(indirizzoId, payload);
    }
}
