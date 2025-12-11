package sop.local.auditlog.domain.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditlogDomainConfig {
    @Bean
    AuditlogDomain auditlogDomain() {
        return new AuditlogDomainService();
    }
}
