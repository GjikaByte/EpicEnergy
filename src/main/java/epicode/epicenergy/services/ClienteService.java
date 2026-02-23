package epicode.epicenergy.services;

import epicode.epicenergy.entities.Cliente;
import epicode.epicenergy.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Cliente trovaPerId(Long id){
        return clienteRepository.findById(id).orElse(null);
    }

    public void eliminaCliente(Long id){
        clienteRepository.deleteById(id);
    }

}
