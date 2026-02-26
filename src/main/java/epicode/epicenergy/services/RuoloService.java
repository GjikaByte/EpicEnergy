package epicode.epicenergy.services;

import epicode.epicenergy.DTOs.ClienteDTO;
import epicode.epicenergy.DTOs.RuoloDTO;
import epicode.epicenergy.entities.Cliente;
import epicode.epicenergy.entities.Ruolo;
import epicode.epicenergy.entities.Utente;
import epicode.epicenergy.repositories.RuoloRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RuoloService {

    private final RuoloRepository ruoloRepository;

    @Autowired
    public RuoloService (RuoloRepository ruoloRepository){
        this.ruoloRepository = ruoloRepository;
    }

    public Ruolo salvaRuolo(RuoloDTO payload){
        Ruolo newRuolo = new Ruolo( payload.getRuolo());
        Ruolo savedRuolo = ruoloRepository.save(newRuolo);
        log.info("Il ruolo "+ savedRuolo.getRuolo() + " e' stato salvato correttamente");
        return savedRuolo;
    }
    public void findByIdAndDelete(UUID ruoloId){
        this.findByIdAndDelete(ruoloId);
    }

    public Ruolo findByRole(String ruolo){
         Ruolo found = ruoloRepository.findByRuolo(ruolo).orElseThrow();
         return found;
    }
}
