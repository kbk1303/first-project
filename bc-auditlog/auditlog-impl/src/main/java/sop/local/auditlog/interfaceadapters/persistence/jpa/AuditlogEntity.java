package sop.local.auditlog.interfaceadapters.persistence.jpa;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import sop.local.enums.AuditSeverity;

@Entity
@Table(name = "auditlog")
public class AuditlogEntity {
    @Id
    private UUID id;

    @Column(name = "user_identifier", nullable = false)
    private String userIdentifier;

    @Column(name = "severity", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuditSeverity severity;

    protected AuditlogEntity() {} // JPA requires a default constructor

    public AuditlogEntity(UUID id, String userIdentifier, AuditSeverity severity) {
        this.id = id;
        this.userIdentifier = userIdentifier;
        this.severity = severity;
    }
    
    //Getter and Setter
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getUserIdentifier() {
        return userIdentifier;
    }
    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }
    public AuditSeverity getSeverity() {
        return severity;
    }
    public void setSeverity(AuditSeverity severity) {
        this.severity = severity;
    }




}
