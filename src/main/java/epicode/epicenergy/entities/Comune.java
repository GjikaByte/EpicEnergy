package epicode.epicenergy.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="comuni")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private String id;

    private String codiceProvincia;
    private String codiceComune;
    private String denominazione;
    private String provincia;


    public Comune(String codiceProvincia, String codiceComune, String denominazione, String provincia) {
        this.codiceProvincia = codiceProvincia;
        this.codiceComune = codiceComune;
        this.denominazione = denominazione;
        this.provincia = provincia;
    }

}
