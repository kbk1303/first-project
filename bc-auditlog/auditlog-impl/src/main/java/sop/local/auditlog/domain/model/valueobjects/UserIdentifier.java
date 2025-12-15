package sop.local.auditlog.domain.model.valueobjects;

import java.util.Locale;

/**
 * Value Object representing the key of an Audit Log entry.
 * invariant: AuditKey must be immutable, Valid, Not Null, Unilogin or ANONYMOUS.
 */


public record UserIdentifier(String value){

    
    public UserIdentifier{
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("UserIdentifier value cannot be null or blank");
        }
        value = value.trim().toUpperCase(Locale.ROOT);
    }

}
