package epicode.epicenergy.services;

import epicode.epicenergy.DTOs.StatoDTO;
import epicode.epicenergy.entities.Stato;
import epicode.epicenergy.repositories.StatoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class StatoService {

    private final StatoRepository statoRepository;

    @Autowired
    public StatoService (StatoRepository statoRepository){
        this.statoRepository = statoRepository;
    }

    public Stato salvaStato(StatoDTO payload){
        Stato newStato = new Stato( payload.getStato());
        Stato savedStato = statoRepository.save(newStato);
        log.info("lo stato "+ savedStato.getStato() + " e' stato salvato correttamente");
        return savedStato;
    }
    public void findByIdAndDelete(UUID statoId){
        statoRepository.deleteById(statoId);
    }

    public Stato findByStato(String stato){
        Stato found = statoRepository.findByStato(stato).orElseThrow();
        return found;
    }
}
