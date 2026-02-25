package epicode.epicenergy.DTOs;

import epicode.epicenergy.entities.TipoCliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClienteDTO (
    @NotBlank(message = "La ragione sociale è obbligatoria")
    String ragioneSociale,
    @NotBlank(message = "La partita IVA è obbligatoria")
    @Size(min = 11, max = 11, message = "La partita IVA deve contenere 11 caratteri")
    String partitaIva,
    @NotBlank(message = "L'email è obbligatoria")
    @Email
    String email,
    @NotNull
    LocalDate dataInserimento,
    @NotNull
    LocalDate dataUltimoContatto,
    @NotNull(message = "Il fatturato annuale è obbligatorio")
    Double fatturatoAnnuale,
    @Email
    String pec,
    @NotBlank(message = "Il telefono è obbligatorio")
    @Size(max = 20, message = "Il telefono non può superare 20 caratteri")String telefono,
    @Email
    String emailContatto,
    @NotBlank(message = "Il nome del contatto è obbligatorio")
    String nomeContatto,
    @NotBlank(message = "Il cognome del contatto è obbligatorio")
    String cognomeContatto,
    @NotBlank(message = "Il telefono del contatto è obbligatorio")
    @Size(max = 20, message = "Il numero di telefono non può superare i 20 caratteri")
    String telefonoContatto,
    @NotNull(message="è obbligatorio specificare il tipo di cliente")
    TipoCliente tipoCliente
){
}