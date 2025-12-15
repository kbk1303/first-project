package sop.local.auditlog;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import sop.local.auditlog.domain.model.Auditlog;
import sop.local.auditlog.domain.model.valueobjects.UserIdentifier;
import sop.local.auditlog.domain.model.valueobjects.UserIdentifierFactory;
import sop.local.enums.AuditSeverity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserIdentifierFactoryTest {

    @Autowired
    private UserIdentifierFactory factory;

    // -----------------------------
    // Valid identifiers (factory)
    // -----------------------------

    

    @ParameterizedTest
    @MethodSource("validIdentifiers")
    void create_acceptsValidIdentifier_andNormalizes(String raw, String expectedNormalized) {
        UserIdentifier id = assertDoesNotThrow(
                () -> factory.create(raw),
                "Factory should not throw for valid identifier: " + raw
        );
        assertEquals(expectedNormalized, id.value());
    }

    private static Stream<Arguments> validIdentifiers() {
        return Stream.of(
                // Simple lower case -> upper case
                Arguments.of("kkri", "KKRI"),

                // Your existing examples with spaces / various forms
                Arguments.of(" ZBC24BESP", "ZBC24BESP"),
                Arguments.of(" BENJ0743", "BENJ0743"),
                Arguments.of(" NOAH2539", "NOAH2539"),
                Arguments.of("ZBC24MNPET", "ZBC24MNPET"),
                Arguments.of("SEBA077D", "SEBA077D"),
                Arguments.of("SIMO401T", "SIMO401T"),
                Arguments.of(" KKRI", "KKRI"),
                Arguments.of("KHH", "KHH"),
                Arguments.of("MSTE.ZBC", "MSTE.ZBC"),
                Arguments.of("CJ.ZBC", "CJ.ZBC"),
                Arguments.of(" CMH.ZBC", "CMH.ZBC"),
                Arguments.of("ANONYMOUS", "ANONYMOUS")
        );
    }

    // -----------------------------
    // Invalid identifiers (factory)
    // -----------------------------

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "   ",           // blank
            "not-valid-123", // clearly invalid
            "ZBC24",         // too short / pattern mismatch
            "ÆØÅ12345",      // depends on your regex but likely invalid
    })
    void create_rejectsInvalidIdentifier(String raw) {
        assertThrows(IllegalArgumentException.class,
                     () -> factory.create(raw),
                     () -> "Factory should reject identifier: '" + raw + "'");
    }

    // -----------------------------
    // Optional: VO usage in Auditlog builder
    // (behaviour should match factory produced VO)
    // -----------------------------

    @ParameterizedTest
    @MethodSource("validIdentifiers")
    void auditlog_builder_acceptsValidUserIdentifier(String raw, String expectedNormalized) {
        // Here we simulate the domain usage where a UserIdentifier is already created
        UserIdentifier id = factory.create(expectedNormalized);

        assertDoesNotThrow(
                () -> Auditlog.builder()
                        .userIdentifier(id)
                        .severity(AuditSeverity.INFO)
                        .build(),
                "Auditlog builder should accept valid UserIdentifier: " + expectedNormalized
        );
    }

    // -----------------------------
    // Old-style “smoke tests” kept as simple non-parameterized sanity checks
    // -----------------------------
    
    @Test
    void simple_acceptsValidIdentifier() {
        UserIdentifier id = factory.create("kkri");
        assertEquals("KKRI", id.value());
    }

    @Test
    void simple_rejectsInvalidIdentifier() {
        assertThrows(IllegalArgumentException.class,
                     () -> factory.create("not-valid-123"));
    }
}
