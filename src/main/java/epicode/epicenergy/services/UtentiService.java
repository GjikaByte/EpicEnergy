package epicode.epicenergy.services;


import epicode.epicenergy.DTOs.UtenteDTO;
import epicode.epicenergy.entities.Utente;
import epicode.epicenergy.exceptions.BadRequestException;
import epicode.epicenergy.exceptions.NotFoundException;
import epicode.epicenergy.repositories.UtentiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UtentiService {

    private final UtentiRepository utentiRepository;

    @Autowired
    public UtentiService(UtentiRepository utentiRepository) {
        this.utentiRepository = utentiRepository;
    }

    // FIND BY EMAIL
    public Utente findByEmail (String email) {
        return this.utentiRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("L'utente con email " + email + " non è stato trovato!"));
    }

    //SAVE UTENTE
    public Utente saveUtente(UtenteDTO payload){
// CONTROLLO EMAIL
        this.utentiRepository.findByEmail(payload.email()).ifPresent(utente -> {
            throw new BadRequestException("L'email "+ utente.getEmail() + " è già registrata!");});
//NUOVO UTENTE
        Utente newUtente = new Utente(payload.username(),payload.nome(),payload.cognome(),payload.email(), payload.password());
//SALVO UTENTE
        Utente savedUtente = this.utentiRepository.save(newUtente);
//LOG
        log.info("Utente"+newUtente.getNome()+" "+newUtente.getCognome() +" salvato con successo: ");
        return savedUtente;
    }


    //FIND BY ID
    public Utente findById(UUID utenteId){
        return this.utentiRepository.findById(utenteId)
                .orElseThrow(()-> new NotFoundException(utenteId));
    }








}
