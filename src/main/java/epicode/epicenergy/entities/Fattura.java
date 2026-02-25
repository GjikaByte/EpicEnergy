package epicode.epicenergy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "fatture")
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idFattura;

    private LocalDate data;
    private BigDecimal importo;

    @Column(nullable = false, unique = true)
    private Long numeroFattura;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_stato")
    private Stato stato;



    public Fattura(LocalDate data, BigDecimal importo, Long numeroFattura, Cliente cliente,Stato stato) {
        this.data = data;
        this.importo = importo;
        this.numeroFattura = numeroFattura;
        this.cliente = cliente;
        this.stato = stato;
    }
}
