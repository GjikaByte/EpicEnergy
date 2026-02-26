package epicode.epicenergy.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import epicode.epicenergy.DTOs.ClienteDTO;
import epicode.epicenergy.Specifications.ClienteSpecifications;
import epicode.epicenergy.entities.*;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final Cloudinary cloudinaryUploader;
    private final IndirizzoSedeLegaleRepository indirizzoSedeLegaleRepository;
    private final IndirizzoSedeOperativaRepository indirizzoSedeOperativaRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, Cloudinary cloudinaryUploader, IndirizzoSedeLegaleRepository indirizzoSedeLegaleRepository, IndirizzoSedeOperativaRepository indirizzoSedeOperativaRepository) {
        this.clienteRepository = clienteRepository;
        this.cloudinaryUploader = cloudinaryUploader;
        this.indirizzoSedeLegaleRepository = indirizzoSedeLegaleRepository;
        this.indirizzoSedeOperativaRepository = indirizzoSedeOperativaRepository;
    }




    public Cliente salvaCliente(ClienteDTO payload){

        IndirizzoSedeLegale sedeLegale = indirizzoSedeLegaleRepository
                .findById(payload.indirizzoSedeLegale())
                .orElseThrow(() -> new NotFoundException("Sede legale non trovata"));

        IndirizzoSedeOperativa sedeOperativa = indirizzoSedeOperativaRepository
                .findById(payload.indirizzoSedeOperativa())
                .orElseThrow(() -> new NotFoundException("Sede operativa non trovata"));


        Cliente newCliente = new Cliente(payload.ragioneSociale(),
                payload.partitaIva(),
                payload.email(),
                payload.dataInserimento(),
                payload.dataUltimoContatto(),
                payload.fatturatoAnnuale(),
                payload.pec(),
                payload.telefono(),
                payload.emailContatto(),
                payload.nomeContatto(),
                payload.cognomeContatto(),
                payload.telefonoContatto(),
                payload.tipoCliente(),
                sedeOperativa,
                sedeLegale
                );
        newCliente.setLogoAziendale("https://ui-avatars.com/api/?name="+payload.nomeContatto()+"+"+payload.cognomeContatto());
        Cliente savedCliente = clienteRepository.save(newCliente);
        log.info("Il Cliente "+newCliente.getNomeContatto()+" "+newCliente.getCognomeContatto() + "è stato salvato correttamente!");
        return savedCliente;
    }

    public Cliente findByIdAndUpdate(UUID clienteId, ClienteDTO payload) {
        Cliente found = this.findById(clienteId);

        if (!found.getEmail().equals(payload.email())) {
            clienteRepository.findByEmail(payload.email()).ifPresent(cliente -> {
                throw new BadRequestException(
                        "L'email " + cliente.getEmail() + " è già in uso!");
            });
        }
        found.setRagioneSociale(payload.ragioneSociale());
        found.setPartitaIva(payload.partitaIva());
        found.setEmail(payload.email());
        found.setDataInserimento(payload.dataInserimento());
        found.setDataUltimoContatto(payload.dataUltimoContatto());
        found.setFatturatoAnnuale(payload.fatturatoAnnuale());
        found.setPec(payload.pec());
        found.setTelefono(payload.telefono());
        found.setEmailContatto(payload.emailContatto());
        found.setNomeContatto(payload.nomeContatto());
        found.setCognomeContatto(payload.cognomeContatto());
        found.setTelefonoContatto(payload.telefonoContatto());
        found.setTipoCliente(payload.tipoCliente());
        found.setLogoAziendale(
                "https://ui-avatars.com/api/?name="
                        + payload.nomeContatto() + "+"
                        + payload.cognomeContatto()
        );
        found.setIndirizzoSedeLegale(indirizzoSedeLegaleRepository
                .findById(payload.indirizzoSedeLegale())
                .orElseThrow(() -> new NotFoundException("Sede legale non trovata")));

        found.setIndirizzoSedeOperativa(indirizzoSedeOperativaRepository
                .findById(payload.indirizzoSedeOperativa())
                .orElseThrow(() -> new NotFoundException("Sede operativa non trovata")));

        Cliente modifiedCliente = clienteRepository.save(found);

        log.info("Il cliente con id "+modifiedCliente.getNomeContatto()+" "+modifiedCliente.getCognomeContatto()+" è stato modificato con successo");

        return modifiedCliente;
    }

    //FIND ALL
    public Page<Cliente> findAll(int page, int size, String orderBy, String sortCriteria) {
            if (size > 100 || size <= 0) size = 10;
            if (page < 0) page = 0;
            List<String> tipoOrdinamento = List.of(
                    "nomeContatto",
                    "fatturatoAnnuale",
                    "dataInserimento",
                    "dataUltimoContatto"
            );

            if (!tipoOrdinamento.contains(orderBy)) {
                orderBy = "nomeContatto";
            }

            Sort sort = sortCriteria.equalsIgnoreCase("desc")
                    ? Sort.by(orderBy).descending()
                    : Sort.by(orderBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);

            return clienteRepository.findAll(pageable);
        }

    //FIND BY ID
    public Cliente findById(UUID clienteId){
        return this.clienteRepository.findById(clienteId)
                .orElseThrow(()-> new NotFoundException(clienteId));
    }

    //    ELIMINA UTENTE
    public void findByIdAndDelete(UUID clienteId){
        Cliente found = this.findById(clienteId);
        this.clienteRepository.delete(found);
    }


    //    UPLOAD LOGO
    public Cliente uploadLogo(UUID clienteId, MultipartFile file){

        Cliente found = this.findById(clienteId);

        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            String imageUrl = (String) result.get("secure_url");

            found.setLogoAziendale(imageUrl);


            return clienteRepository.save(found);


        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void inattivaCliente(UUID id){
        Cliente found = clienteRepository.findById(id).orElseThrow();
        found.setStatoCliente(StatoCliente.INATTIVO);
    }


//    FILTRO CLIENTI
    public Page<Cliente> filtraClienti(
            String ragioneSociale,
            Double fatturatoMin,
            LocalDate dataInserimento,
            LocalDate dataUltimoContatto,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Specification<Cliente> spec = Specification
                .where(ClienteSpecifications.ragioneSociale(ragioneSociale))
                .and(ClienteSpecifications.fatturatoMaggioreDi(fatturatoMin))
                .and(ClienteSpecifications.inseritoDopo(dataInserimento))
                .and(ClienteSpecifications.ultimoContattoPrima(dataUltimoContatto));

        return clienteRepository.findAll(spec, pageable);
    }




}
