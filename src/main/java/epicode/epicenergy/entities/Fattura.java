package epicode.epicenergy.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fatture")
public class Fattura {
    @UUID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private UUID idFattura;
    private LocalDate data;
    private Double importo;
    private Long numeroFattura;
    @Enumerated(EnumType.STRING)
    private StatoFattura statoFattura;
    @ManyToOne
    private Cliente clienteId;
}
