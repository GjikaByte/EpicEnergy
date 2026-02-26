package epicode.epicenergy.controllers;

import epicode.epicenergy.DTOs.ClienteDTO;
import epicode.epicenergy.entities.Cliente;
import epicode.epicenergy.exceptions.ValidationException;
import epicode.epicenergy.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_UTENTE')")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvaCliente(@RequestBody @Validated ClienteDTO payload, BindingResult validationResult){
        if(validationResult.hasErrors()){
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.clienteService.salvaCliente(payload);
        }
    }

    @GetMapping
    public Page<Cliente> findAll( @RequestParam(defaultValue = "0")int page,
                                    @RequestParam(defaultValue = "10")int size,
                                    @RequestParam(defaultValue = "nomeContatto")String orderBy,
                                    @RequestParam(defaultValue = "asc")String sortCriteria) {
        return clienteService.findAll(page, size, orderBy, sortCriteria);
    }

    @GetMapping("/{clienteId}")
    public Cliente getClienteId(@PathVariable UUID clienteId) {
        return clienteService.findById(clienteId);
    }

    @PutMapping("/{clienteId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Cliente updateCliente(@PathVariable UUID clienteId, @RequestBody @Validated ClienteDTO payload,BindingResult validationResult) {
        if(validationResult.hasErrors()){
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.clienteService.findByIdAndUpdate(clienteId, payload);
        }
    }

    @DeleteMapping("/{clientiId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDipendente(@PathVariable UUID clienteId) {
        this.clienteService.findByIdAndDelete(clienteId);
    }

    @PatchMapping("/{clienteId}/logo")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Cliente uploadImage(@RequestParam("profile_picture") MultipartFile file, @PathVariable UUID clienteId){

        Cliente url=this.clienteService.uploadLogo(clienteId,file);

        return url;
    }
}