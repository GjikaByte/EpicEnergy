package epicode.epicenergy.controllers;

import epicode.epicenergy.DTOs.ClienteDTO;
import epicode.epicenergy.entities.Cliente;
import epicode.epicenergy.exceptions.NotFoundException;
import epicode.epicenergy.services.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @PostMapping
    public Cliente salvaCliente(@RequestBody ClienteDTO clienteDTO){

        Cliente cliente = new Cliente();

        cliente.setRagioneSociale(clienteDTO.ragioneSociale);
        cliente.setPartitaIva(clienteDTO.partitaIva);
        cliente.setEmail(clienteDTO.email);
        cliente.setDataInserimento(clienteDTO.dataInserimento);
        cliente.setDataUltimoContatto(clienteDTO.dataUltimoContatto);
        cliente.setFatturatoAnnuale(clienteDTO.fatturatoAnnuale);
        cliente.setPec(clienteDTO.pec);
        cliente.setTelefono(clienteDTO.telefono);
        cliente.setEmailContatto(clienteDTO.emailContatto);
        cliente.setNomeContatto(clienteDTO.nomeContatto);
        cliente.setCognomeContatto(clienteDTO.cognomeContatto);
        cliente.setTelefonoContatto(clienteDTO.telefonoContatto);
        cliente.setLogoAziendale(clienteDTO.logoAziendale);

        return clienteService.salvaCliente(cliente);
    }

    @GetMapping
    public List<Cliente> getClienti(){
        return clienteService.trovaTutti();
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Long id){

        Cliente cliente = clienteService.trovaPerId(id);

        if(cliente == null){
            throw new NotFoundException("Cliente non trovato");
        }

        return cliente;
    }

    @DeleteMapping("/{id}")
    public void eliminaCliente(@PathVariable Long id){
        clienteService.eliminaCliente(id);
    }
}