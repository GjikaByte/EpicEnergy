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
@Table(name = "fatture")
public class Fattura {
    @UUID
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idFattura;
    private LocalDate data;
    private Double importo;
    private Long numeroFattura;
    @Enumerated(EnumType.STRING)
    private StatoFattura statoFattura;
    @ManyToOne
    private Cliente clienteId;

    public Fattura(LocalDate data, Double importo, Long numeroFattura, StatoFattura statoFattura, Cliente clienteId) {
    this.data = data;
    this.importo = importo;
    this.numeroFattura = numeroFattura;
    this.statoFattura = statoFattura;
    this.clienteId = clienteId;
}}
