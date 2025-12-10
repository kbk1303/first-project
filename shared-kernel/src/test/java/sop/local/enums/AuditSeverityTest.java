package sop.local.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuditSeverityTest {

    @Test
    @DisplayName("values() should contain exactly the expected constants in a stable order")
    void happypath_enumValues_ShouldReturnAllValues() {
        List<AuditSeverity> expected = List.of(
            AuditSeverity.INFO,
            AuditSeverity.WARNING,
            AuditSeverity.ERROR
        );
        assertEquals(expected, List.of(AuditSeverity.values()), 
        "Enum constants changed (added/removed/reordered). Review callers and update test if intentional.");
    }
    @Test
    void testFromStringValidInputs() {
        assertEquals(AuditSeverity.INFO, AuditSeverity.fromString("info"));
        assertEquals(AuditSeverity.WARNING, AuditSeverity.fromString("warning"));
        assertEquals(AuditSeverity.ERROR, AuditSeverity.fromString(" error "));
    }

    @Test
    void testFromStringInvalidInput() {
        assertThrows(IllegalStateException.class, 
            () -> AuditSeverity.fromString("INVALID"));
    }

    @Test
    void testFromStringNullInput() {
        assertThrows(IllegalArgumentException.class, 
            () -> AuditSeverity.fromString(null));
    }
}
