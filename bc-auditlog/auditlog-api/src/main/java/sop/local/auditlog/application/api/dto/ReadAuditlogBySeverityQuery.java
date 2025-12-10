package sop.local.auditlog.application.api.dto;

import sop.local.enums.AuditSeverity;

public record ReadAuditlogBySeverityQuery(AuditSeverity severity) {}
