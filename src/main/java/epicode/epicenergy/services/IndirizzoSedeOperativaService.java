package epicode.epicenergy.services;


import epicode.epicenergy.DTOs.IndirizzoDTO;
import epicode.epicenergy.entities.Cliente;
import epicode.epicenergy.entities.IndirizzoSedeOperativa;
import epicode.epicenergy.exceptions.BadRequestException;
import epicode.epicenergy.exceptions.NotFoundException;
import epicode.epicenergy.repositories.ClienteRepository;
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
public class IndirizzoSedeOperativaService {

    private final IndirizzoSedeOperativaRepository indirizzoSedeOperativaRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public IndirizzoSedeOperativaService(IndirizzoSedeOperativaRepository indirizzoSedeOperativaRepository, ClienteRepository clienteRepository) {
        this.indirizzoSedeOperativaRepository = indirizzoSedeOperativaRepository;
        this.clienteRepository = clienteRepository;
    }

    public IndirizzoSedeOperativa save(IndirizzoDTO payload) {

        this.indirizzoSedeOperativaRepository
                .findEventoByViaAndCivicoAndLocalita(payload.getVia(), payload.getCivico(), payload.getLocalita())
                .ifPresent(indirizzoSedeLegale -> {
                    throw new BadRequestException(
                            "L'indirizzo in via " + indirizzoSedeLegale.getVia() +
                                    " con civico " + indirizzoSedeLegale.getCivico() + " in localita' " + indirizzoSedeLegale.getLocalita() + " esiste già!"
                    );
                });

        Cliente cliente = clienteRepository.findById(payload.getClienteId())
                .orElseThrow(() -> new NotFoundException(payload.getClienteId()));


        IndirizzoSedeOperativa newIndirizzoSedeOperativa = new IndirizzoSedeOperativa(payload.getVia(),payload.getCivico(),payload.getLocalita(),payload.getCap(), payload.getComune());

        IndirizzoSedeOperativa savedIndirizzoSedeOperativa = this.indirizzoSedeOperativaRepository.save(newIndirizzoSedeOperativa);
        log.info("L'indirizzo in via " + newIndirizzoSedeOperativa.getVia() + " a " + newIndirizzoSedeOperativa.getLocalita() + " è stato salvato correttamente con id:" + newIndirizzoSedeOperativa.getId_indirizzo_sede_operativa());
        return savedIndirizzoSedeOperativa;
    }

    public Page<IndirizzoSedeOperativa> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.indirizzoSedeOperativaRepository.findAll(pageable);
    }

    public IndirizzoSedeOperativa findById(UUID indirizzoId) {
        return this.indirizzoSedeOperativaRepository.findById(indirizzoId)
                .orElseThrow(() -> new NotFoundException(indirizzoId));
    }

    public void findByIdAndDelete(UUID indirizzoId) {
        IndirizzoSedeOperativa found = this.findById(indirizzoId);
        this.indirizzoSedeOperativaRepository.delete(found);
        log.info("L'indirizzo con id " + indirizzoId + " è stato eliminato correttamente");

    }

    public IndirizzoSedeOperativa findByIdAndUpdate(UUID dipendenteId, IndirizzoDTO payload) {
        IndirizzoSedeOperativa found = this.findById(dipendenteId);

        found.setVia(payload.getVia());
        found.setCivico(payload.getCivico());
        found.setLocalita(payload.getLocalita());
        found.setCap(payload.getCap());
        found.setComune(payload.getComune());

        IndirizzoSedeOperativa modifiedIndirizzoSedeOperativa = this.indirizzoSedeOperativaRepository.save(found);

        log.info("L'indirizzo con id " + modifiedIndirizzoSedeOperativa.getId_indirizzo_sede_operativa() + " è stato modificato correttamente");

        return modifiedIndirizzoSedeOperativa;
    }
}
