package sop.local.auditlog.application.api.dto;

import java.util.UUID;

import sop.local.enums.AuditSeverity;

public record AuditlogResponse(UUID id, String userIdentifier, AuditSeverity severity) {}
