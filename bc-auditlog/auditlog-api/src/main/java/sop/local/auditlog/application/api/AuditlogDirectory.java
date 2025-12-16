package sop.local.auditlog.application.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import sop.local.auditlog.application.api.dto.*;
import sop.local.enums.AuditSeverity;

public interface AuditlogDirectory {
    CreatedAuditlogResult createAuditlog(CreateAuditlogCmd cmd);
    Optional<AuditlogResponse> findById(ReadAuditlogByIdQuery query);
    List<AuditlogResponse> findAll();
    List<AuditlogResponse> findBySearchParams(UUID id, String userIdentifier, AuditSeverity severity);
    int setUserlogsToAnonymous(String userIdentifier);



}
