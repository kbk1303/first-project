package sop.local.auditlog.domain.model.valueobjects;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Value Object representing the key of an Audit Log entry.
 * invariant: AuditKey must be immutable, Valid, Not Null, Unilogin or ANONYMOUS.
 */
public record UserIdentifier(String value) {
    
    public UserIdentifier {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("UserIdentifier value cannot be null or blank");
        }
        value = value.trim().toUpperCase(Locale.ROOT);
        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid UserIdentifier format: " + value);
        }
        // Additional validation logic can be added here
    }

    private boolean isValid(String value) {
         // Static pattern - kan ændres via system property hvis nødvendigt
        String patternString = System.getProperty("organization.login.pattern"  );
        Pattern UNI_LOGIN = Pattern.compile(
         patternString,Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
        ); //unilogin regular expression
        return UNI_LOGIN.matcher(value).matches();
    }
}
