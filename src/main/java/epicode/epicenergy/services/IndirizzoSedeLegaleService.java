package epicode.epicenergy.services;


import epicode.epicenergy.DTOs.IndirizzoDTO;
import epicode.epicenergy.entities.Cliente;
import epicode.epicenergy.entities.IndirizzoSedeLegale;
import epicode.epicenergy.entities.IndirizzoSedeOperativa;
import epicode.epicenergy.exceptions.BadRequestException;
import epicode.epicenergy.exceptions.NotFoundException;
import epicode.epicenergy.repositories.ClienteRepository;
import epicode.epicenergy.repositories.IndirizzoSedeLegaleRepository;
import epicode.epicenergy.repositories.IndirizzoSedeOperativaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class IndirizzoSedeLegaleService {

    private final IndirizzoSedeLegaleRepository indirizzoSedeLegaleRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public IndirizzoSedeLegaleService(IndirizzoSedeLegaleRepository indirizzoSedeLegaleRepository, ClienteRepository clienteRepository) {
        this.indirizzoSedeLegaleRepository = indirizzoSedeLegaleRepository;
        this.clienteRepository = clienteRepository;
    }

    public IndirizzoSedeLegale save(IndirizzoDTO payload) {

        this.indirizzoSedeLegaleRepository
                .findEventoByViaAndCivicoAndLocalita(payload.getVia(), payload.getCivico(), payload.getLocalita())
                .ifPresent(indirizzoSedeLegale -> {
                    throw new BadRequestException(
                            "L'indirizzo in via " + indirizzoSedeLegale.getVia() +
                                    " con civico " + indirizzoSedeLegale.getCivico() + " in localita' " + indirizzoSedeLegale.getLocalita() + " esiste già!"
                    );
                });

//        Cliente cliente = clienteRepository.findById(payload.getClienteId())
//                .orElseThrow(() -> new NotFoundException(payload.getClienteId()));


        IndirizzoSedeLegale newIndirizzoSedeLegale = new IndirizzoSedeLegale(payload.getVia(),payload.getCivico(),payload.getLocalita(),payload.getCap(),payload.getComune());

        IndirizzoSedeLegale savedIndirizzoSedeLegale = this.indirizzoSedeLegaleRepository.save(newIndirizzoSedeLegale);
        log.info("L'indirizzo in via " + newIndirizzoSedeLegale.getVia() + " a " + newIndirizzoSedeLegale.getLocalita() + " è stato salvato correttamente con id:" + newIndirizzoSedeLegale.getId_indirizzo_sede_legale());
        return savedIndirizzoSedeLegale;
    }

    public Page<IndirizzoSedeLegale> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.indirizzoSedeLegaleRepository.findAll(pageable);
    }

    public IndirizzoSedeLegale findById(UUID indirizzoId) {
        return this.indirizzoSedeLegaleRepository.findById(indirizzoId)
                .orElseThrow(() -> new NotFoundException(indirizzoId));
    }

    public void findByIdAndDelete(UUID indirizzoId) {
        IndirizzoSedeLegale found = this.findById(indirizzoId);
        this.indirizzoSedeLegaleRepository.delete(found);
        log.info("L'indirizzo con id " + indirizzoId + " è stato eliminato correttamente");

    }

    public IndirizzoSedeLegale findByIdAndUpdate(UUID dipendenteId, IndirizzoDTO payload) {
        IndirizzoSedeLegale found = this.findById(dipendenteId);

        found.setVia(payload.getVia());
        found.setCivico(payload.getCivico());
        found.setLocalita(payload.getLocalita());
        found.setCap(payload.getCap());
        found.setComune(payload.getComune());

        IndirizzoSedeLegale modifiedIndirizzoSedeLegale = this.indirizzoSedeLegaleRepository.save(found);

        log.info("L'indirizzo con id " + modifiedIndirizzoSedeLegale.getId_indirizzo_sede_legale() + " è stato modificato correttamente");

        return modifiedIndirizzoSedeLegale;
    }
}
