package epicode.epicenergy.services;

import epicode.epicenergy.entities.Cliente;
import epicode.epicenergy.entities.StatoCliente;
import epicode.epicenergy.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public Cliente salvaCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public List<Cliente> trovaTutti(){
        return clienteRepository.findAll();
    }

    public Cliente trovaPerId(UUID id){
        return clienteRepository.findById(id).orElse(null);
    }

    public void eliminaCliente(UUID id){
        clienteRepository.deleteById(id);
    }

    public void inattivaCliente(UUID id){
        Cliente found = clienteRepository.findById(id).orElseThrow();
        found.setStatocliente(StatoCliente.INATTIVO);
    }


}
