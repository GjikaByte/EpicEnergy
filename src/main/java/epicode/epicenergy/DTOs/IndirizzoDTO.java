package epicode.epicenergy.DTOs;

import epicode.epicenergy.entities.Comune;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class IndirizzoDTO {
    @NotBlank(message = "La via e' un campo obbligatorio")
    @Size(min = 2, max = 30, message = "La via deve essere tra i 2 e i 30 caratteri")
    private String via;
    @NotNull(message = "Il numero civico e' un campo obbligatorio")
    private int civico;
    @NotBlank(message = "La localita' un campo obbligatorio")
    @Size(min = 2, max = 30, message = "La localita' deve essere tra i 2 e i 30 caratteri")
    private String localita;
    @NotNull(message = "Il cap e' un campo obbligatorio")
    private long cap;
    @NotNull(message = "Il comune e' un campo obbligatorio")
    private String comuneId;;


}
