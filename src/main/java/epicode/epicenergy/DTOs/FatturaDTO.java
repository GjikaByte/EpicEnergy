package epicode.epicenergy.DTOs;

import epicode.epicenergy.entities.Cliente;
import epicode.epicenergy.entities.StatoFattura;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FatturaDTO(
        @NotBlank
        LocalDate data,
        @NotNull
        Double importo,
        Long numeroFattura,
        @NotBlank
        StatoFattura statoFattura,
        @NotBlank
        Cliente clienteId
) {
}
