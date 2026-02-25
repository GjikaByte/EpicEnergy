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
@Table(name = "indirizzi_sede_legale")
public class IndirizzoSedeLegale
{
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id_indirizzo_sede_legale;
    @Column(nullable = false)
    private String via;
    @Column( nullable = false)
    private int civico;
    @Column( nullable = false)
    private String localita;
    private long cap;
    private String comune;


    public IndirizzoSedeLegale(String via, int civico, String localita, long cap, String comune) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }
}
