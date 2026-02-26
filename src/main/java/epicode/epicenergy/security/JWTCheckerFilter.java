package epicode.epicenergy.security;

import epicode.epicenergy.entities.Utente;
import epicode.epicenergy.exceptions.UnauthorizedException;
import epicode.epicenergy.services.UtentiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    private final JWTToolsUtente jwtTools;
    private final UtentiService utenteService;

    @Autowired
    public JWTCheckerFilter(JWTToolsUtente jwtTools, UtentiService utenteService) {
        this.jwtTools = jwtTools;
        this.utenteService = utenteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire il token nell'Authorization header nel formato corretto");

        String accessToken = authHeader.replace("Bearer ", "");
        jwtTools.verifyToken(accessToken);
        filterChain.doFilter(request, response);

//        AUTORIZZAZIONE
        UUID utenteId = jwtTools.extractIdFromToken(accessToken);
        Utente authenticatedUtente = utenteService.findById(utenteId);

//       ASSOCIAMO UTENTE
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUtente,null,authenticatedUtente.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
