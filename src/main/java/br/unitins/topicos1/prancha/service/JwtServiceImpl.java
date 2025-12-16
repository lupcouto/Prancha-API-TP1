package br.unitins.topicos1.prancha.service;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import br.unitins.topicos1.prancha.model.Perfil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    // define quanto tempo o token vai durar
    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    // método que vai gerar o jwt
    @Override
    public String generateJwt(String login, Perfil perfil) {

        // data de expiração do token: pega o horário atual e soma com mais 24h
        Instant expiryDate = Instant.now().plus(EXPIRATION_TIME);

        // definindo autorização de perfis através de roles
        Set<String> roles = new HashSet<String>();
        roles.add(perfil.name());

        // construção do jwt
        return Jwt.issuer("unitins-jwt")
                .subject(login)
                .groups(roles)
                .expiresAt(expiryDate)
                .sign();

    }
    
}