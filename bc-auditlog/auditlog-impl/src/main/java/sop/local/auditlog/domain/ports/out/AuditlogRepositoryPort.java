package sop.local.auditlog.domain.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import sop.local.auditlog.domain.model.Auditlog;
import sop.local.auditlog.domain.model.valueobjects.AuditlogId;
import sop.local.enums.AuditSeverity;

public interface AuditlogRepositoryPort {
    Auditlog save(Auditlog auditlog);
    List<Auditlog> findBySearchParams(UUID id, String userIdentifier, AuditSeverity severity);
    List<Auditlog> findAll();
    Optional<Auditlog> findById(AuditlogId auditlogId);
    int anonymizeUserForLogs(List<UUID> ids);
}
