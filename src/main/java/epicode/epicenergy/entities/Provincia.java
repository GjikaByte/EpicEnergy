package epicode.epicenergy.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="province")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private String id;

    private String sigla;
    private String provincia;
    private String regione;

    public Provincia(String sigla, String provincia, String regione) {
        this.sigla = sigla;
        this.provincia = provincia;
        this.regione = regione;
    }
}
