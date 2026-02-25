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
@Table(name = "indirizzi_sede_operativa")
public class IndirizzoSedeOperativa
{
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id_indirizzo_sede_operativa;
    @Column(nullable = false)
    private String via;
    @Column( nullable = false)
    private int civico;
    @Column( nullable = false)
    private String localita;
    private long cap;

    @ManyToOne
    @JoinColumn(name="id_comune")
    private Comune comune;


    public IndirizzoSedeOperativa(String via, int civico, String localita, long cap, Comune comune) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }
}
