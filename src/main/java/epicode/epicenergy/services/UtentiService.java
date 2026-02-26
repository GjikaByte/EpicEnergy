package epicode.epicenergy.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import epicode.epicenergy.DTOs.UtenteDTO;
import epicode.epicenergy.entities.Ruolo;
import epicode.epicenergy.entities.Utente;
import epicode.epicenergy.exceptions.BadRequestException;
import epicode.epicenergy.exceptions.NotFoundException;
import epicode.epicenergy.repositories.UtentiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class UtentiService {

    private final UtentiRepository utentiRepository;
    private final Cloudinary cloudinaryUploader;
    private final PasswordEncoder bcrypt;
    private final RuoloService ruoloService;

    @Autowired
    public UtentiService(UtentiRepository utentiRepository,Cloudinary cloudinaryUploader,PasswordEncoder bcrypt, RuoloService ruoloService) {
        this.utentiRepository = utentiRepository;
        this.cloudinaryUploader = cloudinaryUploader;
        this.bcrypt = bcrypt;
        this.ruoloService = ruoloService;
    }

    public Utente save(UtenteDTO payload) {

        this.utentiRepository.findByEmail(payload.email()).ifPresent(utente -> {
            throw new BadRequestException("L'email " + utente.getEmail() + " è già in uso!");
        });

        Ruolo found = ruoloService.findByRole("UTENTE");

        Utente newUtente = new Utente(
                payload.username(),
                payload.nome(),
                payload.cognome(),
                payload.email(),
                bcrypt.encode(payload.password()),
                found
        );


        Utente savedUtente = this.utentiRepository.save(newUtente);

        log.info("L'Admin con id "+savedUtente.getId()+" è stato salvato con successo!");

        return savedUtente;
    }

    // FIND BY EMAIL
    public Utente findByEmail (String email) {
        return this.utentiRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("L'utente con email " + email + " non è stato trovato!"));
    }



    //FIND BY ID
    public Utente findById(UUID utenteId){
        return this.utentiRepository.findById(utenteId)
                .orElseThrow(()-> new NotFoundException(utenteId));
    }



    //FIND ALL
    public Page<Utente> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.utentiRepository.findAll(pageable);
    }

    // FIND ALL NO PAGINATION
    public List<Utente> findAllNoPagination() {
        return this.utentiRepository.findAll();
    }

    //MODIFICA UTENTE
    public Utente findByIdAndUpdate(UUID userId, UtenteDTO payload){
        //CERCO UTENTE
        Utente found = this.findById(userId);
       //VALIDAZIONE DATI
        if(!found.getEmail().equals(payload.email()))this.utentiRepository.findByEmail(payload.email()).ifPresent(utente -> {
            throw new BadRequestException("L'email "+utente.getEmail()+" è già in uso!");
        });
       //MODIFICO UTENTE
        found.setUsername(payload.username());
        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setEmail(payload.email());
        found.setAvatar("https://ui-avatars.com/api?name=" + payload.nome() + "+" + payload.cognome());
        //SALVO
        Utente modifiedUtente = utentiRepository.save(found);
        //LOG
        log.info("L'utente con id "+modifiedUtente.getId()+" è stato modificato con successo!");
        //UTENTE MODIFICATO
        return modifiedUtente;
    }


    //    ELIMINA UTENTE
    public void findByIdAndDelete(UUID userId){
        Utente found = this.findById(userId);
        this.utentiRepository.delete(found);
    }


    //    UPLOAD AVATAR
    public Utente uploadAvatar(UUID utenteId, MultipartFile file){

        Utente found = this.findById(utenteId);

        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            String imageUrl = (String) result.get("secure_url");

            found.setAvatar(imageUrl);


            return utentiRepository.save(found);


        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public Utente saveAdmin(UtenteDTO payload) {

        this.utentiRepository.findByEmail(payload.email()).ifPresent(utente -> {
            throw new BadRequestException("L'email " + utente.getEmail() + " è già in uso!");
        });

        Ruolo found = ruoloService.findByRole("ADMIN");

        Utente newUtente = new Utente(
                payload.username(),
                payload.nome(),
                payload.cognome(),
                payload.email(),
                bcrypt.encode(payload.password()),
                found
        );


        Utente savedUtente = this.utentiRepository.save(newUtente);

        log.info("L'Admin con id "+savedUtente.getId()+" è stato salvato con successo!");

        return savedUtente;
    }
}
