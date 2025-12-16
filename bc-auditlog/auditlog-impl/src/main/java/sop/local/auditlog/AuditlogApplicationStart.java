package sop.local.auditlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "sop.local.auditlog")
@ConfigurationPropertiesScan(basePackages = "sop.local.auditlog.config")
public class AuditlogApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(AuditlogApplicationStart.class, args);
    }
}
