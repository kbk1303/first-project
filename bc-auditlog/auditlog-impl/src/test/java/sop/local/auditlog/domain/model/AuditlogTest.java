package sop.local.auditlog.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import sop.local.auditlog.domain.model.valueobjects.UserIdentifier;
import sop.local.enums.AuditSeverity;

public class AuditlogTest {


    /******************** happy path *****************/

    @Test
    void happyPath_builderBuild_shoulReturnNewAuditlog() {

        Auditlog auditlog = Auditlog.builder().userIdentifier(new UserIdentifier("INESA")).severity(AuditSeverity.INFO).build();
        assertNotNull(auditlog);

    }

    /************ unhappy path *********************/

    @Test
    void unhappyPath_builder_UserIdentifier_null_shouldThrowException() {
        var ex = assertThrows(IllegalStateException.class, () -> Auditlog.builder().userIdentifier(null).severity(AuditSeverity.ERROR).build());
        assertEquals("UserIdentifier is required", ex.getMessage());
    }

}
