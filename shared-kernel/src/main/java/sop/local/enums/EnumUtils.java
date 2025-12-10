package sop.local.enums;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumUtils {
    private static final Logger logger = LoggerFactory.getLogger(EnumUtils.class);
    
    public static <T extends Enum<T>> T parseEnum(Class<T> enumClass, String value) {
        if (value == null || value.trim().isEmpty()) {
            logger.error("Attempted to parse null or empty string to enum {}", enumClass.getSimpleName());
            throw new IllegalArgumentException(enumClass.getSimpleName() + " cannot be null or empty");
        }
        
        try {
            return Enum.valueOf(enumClass, value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Failed to parse '{}' to enum {}", value, enumClass.getSimpleName());
            throw new IllegalStateException(
                String.format("Invalid %s: '%s'. Valid values: %s", 
                    enumClass.getSimpleName(), value, Arrays.toString(enumClass.getEnumConstants())), e);
        }
    }
}
