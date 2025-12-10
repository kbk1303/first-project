package sop.local.auditlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "sop.local.auditlog")
public class AuditlogApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(AuditlogApplicationStart.class, args);
    }
}
