package be.darkkraft.transferproxy.jwtexample.util;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class HashUtil {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final MessageDigest MESSAGE_DIGEST;

    static {
        try {
            MESSAGE_DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private HashUtil() throws IllegalAccessException {
        throw new IllegalAccessException("You cannot instantiate a utility class");
    }

    public static @NotNull String generateSalt() {
        final byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static @NotNull String hash(final @NotNull String password, final @NotNull String salt) {
        MESSAGE_DIGEST.update(salt.getBytes());
        return Base64.getEncoder().encodeToString(MESSAGE_DIGEST.digest(password.getBytes()));
    }

}