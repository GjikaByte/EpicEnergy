package epicode.epicenergy.DTOs;

import epicode.epicenergy.entities.Provincia;
import jakarta.validation.constraints.NotBlank;

public record ComuneDTO(
        @NotBlank(message = "Il codice provincia è obbligatorio")
        String codiceProvincia,
        @NotBlank(message = "Il codice comune è obbligatorio")
        String codiceComune,
        @NotBlank(message = "La denominazione è obbligatoria")
        String denominazione,
        @NotBlank(message = "La provincia è obbligatoria")
        Provincia provincia
) {
}
