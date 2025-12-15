package sop.local.auditlog.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


import sop.local.auditlog.domain.model.valueobjects.UserIdentifier;
import sop.local.enums.AuditSeverity;

public class AuditlogTest {


    /******************** happy path *****************/

    @Test
    void happyPath_builderBuild_shoulReturnNewAuditlog() {



        System.setProperty("organization.login.pattern","^(?:ANONYMOUS|ZBC\\d{2}[A-Z]{4,5}|[A-ZÆØÅ]{4}\\d{3}[A-ZÆØÅ0-9]|[A-ZÆØÅ]{2,4}\\.ZBC|[A-ZÆØÅ]{3,4})$");

        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier(" ZBC24BESP")).severity(AuditSeverity.INFO).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier(" BENJ0743")).severity(AuditSeverity.INFO).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier(" NOAH2539")).severity(AuditSeverity.WARNING).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier("ZBC24MNPET")).severity(AuditSeverity.ERROR).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier("SEBA077D")).severity(AuditSeverity.INFO).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier("SIMO401T")).severity(AuditSeverity.WARNING).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier(" KKRI")).severity(AuditSeverity.ERROR).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier("KHH")).severity(AuditSeverity.INFO).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier("MSTE.ZBC")).severity(AuditSeverity.INFO).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier("CJ.ZBC")).severity(AuditSeverity.ERROR).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier(" CMH.ZBC")).severity(AuditSeverity.WARNING).build());
        assertDoesNotThrow(() -> Auditlog.builder().userIdentifier(new UserIdentifier("ANONYMOUS")).severity(AuditSeverity.INFO).build());

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
