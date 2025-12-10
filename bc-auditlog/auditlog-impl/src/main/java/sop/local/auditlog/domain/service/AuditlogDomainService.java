package sop.local.auditlog.domain.service;

import sop.local.auditlog.domain.model.Auditlog;
import sop.local.auditlog.domain.model.valueobjects.UserIdentifier;
import sop.local.enums.AuditSeverity;

public class AuditlogDomainService implements AuditlogDomain {

    @Override
    public Auditlog createAuditlog(UserIdentifier userIdentifier, AuditSeverity severity) {
        return Auditlog.builder()
            .userIdentifier(userIdentifier)
            .severity(severity)
            .build();
    }

}
