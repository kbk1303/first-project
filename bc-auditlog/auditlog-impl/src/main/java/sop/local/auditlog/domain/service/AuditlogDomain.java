package sop.local.auditlog.domain.service;

import sop.local.auditlog.domain.model.Auditlog;
import sop.local.auditlog.domain.model.valueobjects.UserIdentifier;
import sop.local.enums.AuditSeverity;

public interface AuditlogDomain {
    Auditlog createAuditlog(UserIdentifier userIdentifier, AuditSeverity severity);
}
