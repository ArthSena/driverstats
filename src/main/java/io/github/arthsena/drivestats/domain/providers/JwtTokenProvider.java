package io.github.arthsena.drivestats.domain.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.arthsena.drivestats.domain.exceptions.UnauthorizedException;
import io.github.arthsena.drivestats.domain.models.User;
import io.github.arthsena.drivestats.infra.security.Subject;
import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.*;

@ApplicationScoped
public class JwtTokenProvider {

    private final String issuer;
    private final Integer expirationTimeInMinutes;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

    public JwtTokenProvider(
            @ConfigProperty(name = "jwt.issuer") String issuer,
            @ConfigProperty(name = "jwt.secret") String secret,
            @ConfigProperty(name = "jwt.expiration.time.minutes") Integer expirationTimeInMinutes
    ){
        this.issuer = issuer;
        this.algorithm = Algorithm.HMAC512(secret);
        this.jwtVerifier = JWT.require(algorithm).withIssuer(issuer).build();
        this.expirationTimeInMinutes = expirationTimeInMinutes;
    }

    public String create(User user) {
        JWTCreator.Builder builder = JWT.create().withIssuer(issuer).withIssuedAt(new Date())
                .withSubject(user.getId().toString())
                .withClaim("name", user.getName())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().toString());

        if (expirationTimeInMinutes != null)
            builder.withExpiresAt(plusMinutes(expirationTimeInMinutes));

        return builder.sign(algorithm);
    }

    public Subject verify(String token) {
        DecodedJWT decodedJWT;

        try{
            decodedJWT = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new UnauthorizedException(ExceptionType.JWT_VERIFICATION_EXCEPTION.withMessage(e.getMessage()));
        }

        return new Subject(
                UUID.fromString(decodedJWT.getSubject()),
                decodedJWT.getClaim("name").asString(),
                decodedJWT.getClaim("email").asString(),
                User.Role.valueOf(decodedJWT.getClaim("role").asString())
        );
    }

    private static Date plusMinutes(int minutes) {
        long oneMinuteInMillis = 60000;
        Calendar calendar = Calendar.getInstance();
        return new Date(calendar.getTimeInMillis() + (minutes * oneMinuteInMillis));
    }
}
