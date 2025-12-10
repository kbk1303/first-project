package sop.local.auditlog.application.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sop.local.auditlog.domain.service.AuditlogDomain;
import sop.local.auditlog.domain.service.AuditlogDomainService;

@Configuration
public class AuditlogDomainConfig {
    @Bean
    AuditlogDomain auditlogDomain() {
        return new AuditlogDomainService();
    }
}
