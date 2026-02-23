package epicode.epicenergy.DTOs;

import epicode.epicenergy.entities.StatoFattura;

import java.time.LocalDate;

public record FatturaDTO(

        LocalDate data,
        Double importo,
        Long numeroFattura,
        StatoFattura statoFattura,
        Cliente clienteId
) {
}
