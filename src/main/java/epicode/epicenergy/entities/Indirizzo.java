package epicode.epicenergy.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "indirizzi")
public class Indirizzo
{
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id_indirizzo;
    @Column(nullable = false)
    private String via;
    @Column( nullable = false)
    private int civico;
    @Column( nullable = false)
    private String localita;
    private long cap;
    private String comune;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public Indirizzo(String via, int civico, String localita, long cap, String comune, Cliente cliente) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
        this.cliente = cliente;
    }
}
