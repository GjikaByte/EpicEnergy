package epicode.epicenergy.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="utenti")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String avatar;


    @ManyToOne
    @JoinColumn(name="id_ruolo")
    private Ruolo ruolo;

    public Utente(String username, String nome, String cognome, String email, String password, Ruolo ruolo) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo= ruolo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + this.ruolo.getRuolo())
        );
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}

