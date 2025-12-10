package sop.local.auditlog.application.api;

import java.util.List;
import java.util.Optional;

import sop.local.auditlog.application.api.dto.*;

public interface AuditlogDirectory {
    CreatedAuditlogResult createAuditlog(CreateAuditlogCmd cmd);
    Optional<AuditlogResponse> findById(ReadAuditlogByIdQuery query);
    List<AuditlogResponse> findByUserIdentifier(ReadAuditlogByUserIdentifierQuery query);
    List<AuditlogResponse> findByAuditSeverity(ReadAuditlogBySeverityQuery query);
    List<AuditlogResponse> findAll();



}
