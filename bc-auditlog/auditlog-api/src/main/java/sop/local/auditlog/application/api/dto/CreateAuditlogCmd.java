package sop.local.auditlog.application.api.dto;


import sop.local.enums.AuditSeverity;

public record CreateAuditlogCmd(
   String userIdentifier,
   AuditSeverity severity

) {}
