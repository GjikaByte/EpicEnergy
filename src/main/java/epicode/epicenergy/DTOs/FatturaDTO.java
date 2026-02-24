package epicode.epicenergy.DTOs;

import epicode.epicenergy.entities.Cliente;

import java.time.LocalDate;

public record FatturaDTO(

        LocalDate data,
        Double importo,
        Long numeroFattura,
        Cliente clienteId
) {
}
