package epicode.epicenergy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "stato")
public class Stato {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_stato")
    private UUID idStato;
    @Column(name = "stato")
    private String stato;
    public Stato(String stato) {
    this.stato = stato;
}
}
