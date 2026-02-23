package epicode.epicenergy.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UtenteDTO(
        @NotBlank(message="L'username è un campo obbligatorio")
        @Size(min=5, max=15,message = "L'username deve essere tra i 5 e i 15 caratteri")
        String username,
        @NotBlank(message="Il nome è un campo obbligatorio")
        @Size(min=3, max=30,message = "Il nome proprio deve essere tra i 3 e i 30 caratteri")
        String nome,
        @NotBlank(message="Il cognome è un campo obbligatorio")
        @Size(min=3, max=30,message = "Il cognome deve essere tra i 3 e i 30 caratteri")
        String cognome,
        @NotBlank(message="L'email è un campo obbligatorio")
        @Size(min=7,message = "L'email deve essere minimo di 7 caratteri")
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{4,}$", message = "La password deve contenere una maiuscola, una minuscola e un numero")
        String password
) {
}
