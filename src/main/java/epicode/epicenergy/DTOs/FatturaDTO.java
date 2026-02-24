package epicode.epicenergy.DTOs;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record FatturaDTO(

        @NotNull
        @PastOrPresent
        LocalDate data,

        @NotNull
        @DecimalMin(value = "0.00", inclusive = false) //obbligato a essere maggiore di 0, non uguale
        @Digits(integer = 10, fraction = 2) //massimo 10 numeri prima della virgola, massimo 2 dopo
        BigDecimal importo,

        @NotNull
        @Positive
        Long numeroFattura,

        @NotNull
        @Positive
        UUID clienteId
) {
}
