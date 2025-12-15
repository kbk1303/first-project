package sop.local.auditlog.domain.model.valueobjects;

import java.util.Locale;
import java.util.regex.Pattern;


public class UserIdentifierFactory {
    private final Pattern uniLoginPattern;

    public UserIdentifierFactory(String patternString) {
        if (patternString == null || patternString.isBlank()) {
            throw new IllegalArgumentException("patternString cannot be null or blank");
        }
        this.uniLoginPattern = Pattern.compile(
                patternString,
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
        );
    }

    public UserIdentifier create(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("UserIdentifier cannot be null or blank");
        }

        String normalized = raw.trim().toUpperCase(Locale.ROOT);

        if (!uniLoginPattern.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Invalid UserIdentifier format: " + raw);
        }

        return new UserIdentifier(normalized);
    }
}
