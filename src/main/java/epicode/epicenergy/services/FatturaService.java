package epicode.epicenergy.services;

import epicode.epicenergy.DTOs.FatturaDTO;
import epicode.epicenergy.entities.Cliente;
import epicode.epicenergy.entities.Fattura;
import epicode.epicenergy.entities.Stato;
import epicode.epicenergy.repositories.ClienteRepository;
import epicode.epicenergy.repositories.FatturaRepository;
import epicode.epicenergy.repositories.StatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FatturaService {
    private final FatturaRepository fatturaRepository;
    private final ClienteRepository clienteRepository;
    private final StatoRepository statoRepository;

    @Autowired
    public FatturaService(FatturaRepository fatturaRepository, ClienteRepository clienteRepository, StatoRepository statoRepository) {
        this.fatturaRepository = fatturaRepository;
        this.clienteRepository = clienteRepository;
        this.statoRepository = statoRepository;
    }

    @Transactional
    public Fattura save(FatturaDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente non trovato"));

        Stato stato = statoRepository.findById(dto.statoId())
                .orElseThrow(() -> new IllegalArgumentException("Stato non trovato"));

        Fattura nuovaFattura = new Fattura(dto.data(), dto.importo(), dto.numeroFattura(), cliente, stato);

        return fatturaRepository.save(nuovaFattura);
    }

    public List<Fattura> findAll() {
        return this.fatturaRepository.findAll();
    }

    public Fattura trovaPerId(UUID id) {
        return this.fatturaRepository.findById(id).orElse(null);
    }

    public void eliminaFattura(UUID id) {
        this.fatturaRepository.deleteById(id);
    }
}
