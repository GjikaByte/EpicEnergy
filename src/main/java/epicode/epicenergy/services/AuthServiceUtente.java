package epicode.epicenergy.services;

import epicode.epicenergy.DTOs.LoginDTO;
import epicode.epicenergy.entities.Utente;
import epicode.epicenergy.exceptions.UnauthorizedException;
import epicode.epicenergy.security.JWTToolsUtente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceUtente {

    private final UtentiService utenteService;
    private final JWTToolsUtente jwtTools;
    private final PasswordEncoder bcrypt;

    @Autowired
    public AuthServiceUtente(UtentiService utenteService, JWTToolsUtente jwtTools, PasswordEncoder bcrypt) {
        this.utenteService = utenteService;
        this.jwtTools = jwtTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        Utente found = this.utenteService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            String accessToken = jwtTools.generateToken(found);
            return accessToken;
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
