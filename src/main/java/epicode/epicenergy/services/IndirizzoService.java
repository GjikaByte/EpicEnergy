package epicode.epicenergy.services;


import epicode.epicenergy.DTOs.IndirizzoDTO;
import epicode.epicenergy.entities.Cliente;
import epicode.epicenergy.entities.Indirizzo;
import epicode.epicenergy.exceptions.BadRequestException;
import epicode.epicenergy.exceptions.NotFoundException;
import epicode.epicenergy.repositories.ClienteRepository;
import epicode.epicenergy.repositories.IndirizzoRepository;
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
public class IndirizzoService {

    private final IndirizzoRepository indirizzoRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public IndirizzoService(IndirizzoRepository indirizzoRepository, ClienteRepository clienteRepository) {
        this.indirizzoRepository=indirizzoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Indirizzo save(IndirizzoDTO payload) {

        this.indirizzoRepository
                .findEventoByViaAndCivicoAndLocalita(payload.getVia(), payload.getCivico(), payload.getLocalita())
                .ifPresent(indirizzo -> {
                    throw new BadRequestException(
                            "L'indirizzo in via " + indirizzo.getVia() +
                                    " con civico " + indirizzo.getCivico() + " in localita' " + indirizzo.getLocalita() + " esiste già!"
                    );
                });

        Cliente cliente = clienteRepository.findById(payload.getClienteId())
                .orElseThrow(() -> new NotFoundException(payload.getClienteId()));


        Indirizzo newIndirizzo = new Indirizzo(payload.getVia(),payload.getCivico(),payload.getLocalita(),payload.getCap(), payload.getComune(),cliente);
        Indirizzo savedIndirizzo = this.indirizzoRepository.save(newIndirizzo);
        log.info("L'indirizzo invia " + newIndirizzo.getVia() + " a " + newIndirizzo.getLocalita() + " è stato salvato correttamente con id:" + newIndirizzo.getId_indirizzo());
        return savedIndirizzo;
    }

    public Page<Indirizzo> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.indirizzoRepository.findAll(pageable);
    }

    public Indirizzo findById(UUID indirizzoId) {
        return this.indirizzoRepository.findById(indirizzoId)
                .orElseThrow(() -> new NotFoundException(indirizzoId));
    }

    public void findByIdAndDelete(UUID indirizzoId) {
        Indirizzo found = this.findById(indirizzoId);
        this.indirizzoRepository.delete(found);
        log.info("L'indirizzo con id " + indirizzoId + " è stato eliminato correttamente");

    }
}
