package sop.local.auditlog.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sop.local.auditlog.domain.model.valueobjects.UserIdentifierFactory;
import sop.local.enums.AuditSeverity;

@SpringBootTest
public class AuditlogTest {


    @Autowired
    private UserIdentifierFactory factory;

    /******************** happy path *****************/

    @Test
    void happyPath_builderBuild_shoulReturnNewAuditlog() {
        

        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create(" ZBC24BESP")).severity(AuditSeverity.INFO).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create(" BENJ0743")).severity(AuditSeverity.INFO).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create(" NOAH2539")).severity(AuditSeverity.WARNING).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create("ZBC24MNPET")).severity(AuditSeverity.ERROR).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create("SEBA077D")).severity(AuditSeverity.INFO).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create("SIMO401T")).severity(AuditSeverity.WARNING).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create(" KKRI")).severity(AuditSeverity.ERROR).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create("KHH")).severity(AuditSeverity.INFO).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create("MSTE.ZBC")).severity(AuditSeverity.INFO).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create("CJ.ZBC")).severity(AuditSeverity.ERROR).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create(" CMH.ZBC")).severity(AuditSeverity.WARNING).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(factory.create("ANONYMOUS")).severity(AuditSeverity.INFO).build());

        // Cleanup
        //System.clearProperty("organization.login.pattern");

    }

    /************ unhappy path *********************/

    @Test
    void unhappyPath_builder_UserIdentifier_null_shouldThrowException() {
        var ex = assertThrows(IllegalStateException.class, () -> Auditlog.builder().userIdentifier(null).severity(AuditSeverity.ERROR).build());
        assertEquals("UserIdentifier is required", ex.getMessage());
    }

}
