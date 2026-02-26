package epicode.epicenergy.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuoloDTO {
    @NotBlank(message = "Il ruolo e' un campo obbligatorio")
    @Size(min = 2, max = 30, message = "Il ruolo deve essere tra i 2 e i 30 caratteri")
    private String ruolo;
}
