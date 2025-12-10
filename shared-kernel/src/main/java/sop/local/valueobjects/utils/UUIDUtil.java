package sop.local.valueobjects.utils;

import java.util.UUID;

public final class UUIDUtil {
    private UUIDUtil() {/* Prevent instantiation */}

    public static UUID parseRequired(String uuidStr) {
        if (uuidStr == null || uuidStr.isBlank()) {
            throw new IllegalArgumentException("UUID string cannot be null or blank");
        }
        try {
            UUID.fromString(uuidStr.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format: " + uuidStr, e);
        }
        return UUID.fromString(uuidStr.trim());
    }

     /** Require non-null UUID value (used by VO compact constructors). */
    public static UUID require(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        return uuid;
    }   

     /** Generate a new random UUID. */
    public static UUID newUuid() {
        return UUID.randomUUID();
    }
}
