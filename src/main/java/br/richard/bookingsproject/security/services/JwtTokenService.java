package br.richard.bookingsproject.security.services;

import br.richard.bookingsproject.errors.exceptions.AuthenticationTokenCreationException;
import br.richard.bookingsproject.errors.exceptions.UnauthorizedException;
import br.richard.bookingsproject.security.dto.UserDetailsDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtTokenService {

    @Value("${default-project.jwt.secret}")
    private String secret;

    @Value("${default-project.jwt.expiration}")
    private Long expiration;

    @Value("${default-project.jwt.issuer}")
    private String issuer;

    private final ZoneOffset zoneOffset = ZoneOffset.of("-03:00");


    public String generateToken(UserDetailsDTO user){
        try{
            var algorithm = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer(this.issuer)
                    .withSubject(user.getUser().getId().toString())
                    .withIssuedAt(LocalDateTime.now().toInstant(zoneOffset))
                    .withExpiresAt(LocalDateTime.now().plusMinutes(this.expiration).toInstant(zoneOffset))
                    .withPayload(Map.of("user", user.toMap()))
                    .sign(algorithm);
        }catch (JWTCreationException jwtCreationException){
            throw new AuthenticationTokenCreationException(jwtCreationException);
        }
    }

    public UUID getUserId(String token){
        try{
            var algorithm = Algorithm.HMAC256(this.secret);

            String id = JWT.require(algorithm)
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getSubject();

            return UUID.fromString(id);

        }catch (JWTVerificationException jwtVerificationException){
            throw new UnauthorizedException(jwtVerificationException);
        }
    }
}
