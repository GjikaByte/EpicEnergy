package epicode.epicenergy.services;

import epicode.epicenergy.DTOs.FatturaDTO;
import epicode.epicenergy.entities.Fattura;
import epicode.epicenergy.repositories.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FatturaService {
    private final FatturaRepository fatturaRepository;
    @Autowired
    public FatturaService(FatturaRepository fatturaRepository) {
    this.fatturaRepository = fatturaRepository;}

    public Fattura save(FatturaDTO dto) {
        Fattura nuovaFattura = new Fattura();
        if (fatturaRepository.existsByNumeroFattura(dto.numeroFattura())){
            throw new IllegalArgumentException("Fattura già esistente");
        }
        return this.fatturaRepository.save(nuovaFattura);
    }

}
