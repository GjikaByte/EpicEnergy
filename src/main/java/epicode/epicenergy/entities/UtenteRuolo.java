package epicode.epicenergy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "utenti_ruolo")
public class UtenteRuolo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUtenteRuolo;
    @ManyToOne
    @JoinColumn(name = "id_stato")
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "id_ruolo")
    private Ruolo ruolo;

public UtenteRuolo(Utente utente, Ruolo ruolo) {
    this.utente = utente;
    this.ruolo = ruolo;

}}
