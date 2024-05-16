package net.transferproxy.jwtexample.wrapper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Objects;

public final class JWTWrapper {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JWTWrapper(final @NotNull String token) {
        this(Algorithm.HMAC256(Objects.requireNonNull(token, "token cannot be null")));
    }

    public JWTWrapper(final @NotNull Algorithm algorithm) {
        this.algorithm = Objects.requireNonNull(algorithm, "algorithm cannot be null");
        this.verifier = JWT.require(this.algorithm).build();
    }

    public String encode(final @NotNull String subject, final @NotNull String input, final @Nullable Instant expiration) {
        final JWTCreator.Builder builder = JWT.create().withSubject(subject);
        if (expiration != null) {
            builder.withExpiresAt(expiration);
        }
        return builder.withClaim("data", input).sign(this.algorithm);
    }

    public String decode(final @NotNull String token) {
        return this.verifier.verify(token).getClaim("data").asString();
    }

}