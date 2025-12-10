package sop.local.auditlog.domain.model;

import sop.local.auditlog.domain.model.valueobjects.*;
import sop.local.enums.AuditSeverity;

public class Auditlog {
    private final AuditlogId auditlogId;
    private final UserIdentifier userIdentifier;
    private final AuditSeverity severity;


    private Auditlog(AuditlogId auditlogId, UserIdentifier userIdentifier, AuditSeverity severity) {
        this.auditlogId = auditlogId;
        this.userIdentifier = userIdentifier;
        this.severity = severity;
    } 
    
    /* With'ers */
    
    public Auditlog withUserIdentifier (UserIdentifier userIdentifier) {
       return new Auditlog(this.auditlogId, userIdentifier, this.severity);
    }

    public Auditlog withAuditSeverity (AuditSeverity severity) {
       return new Auditlog(this.auditlogId, this.userIdentifier, severity);
    } 

    /* Getters */
    public AuditlogId getAuditlogId() {
        return auditlogId;
    }
    
    public UserIdentifier getUserIdentifier() {
        return userIdentifier;
    }   

    public AuditSeverity getSeverity() {
        return severity;
    }       
    

    /*
     * Builder factory innner class
     */

     // -- Builder initializer
    public static Builder builder() { return new Builder();}

    public static class Builder {
        private AuditlogId id;
        private UserIdentifier userIdentifier;
        private AuditSeverity severity;

        public Builder id(AuditlogId id) {
            this.id = id;
            return this;
        }
        
        public Builder userIdentifier(UserIdentifier userIdentifier) {
            this.userIdentifier = userIdentifier;
            return this;
        }

        public Builder severity(AuditSeverity severity) {
            this.severity = severity;
            return this;
        }

        public Auditlog build() {
            if(userIdentifier == null) {
                throw new IllegalStateException("UserIdentifier is required");
            }
            if(severity == null) {
                throw new IllegalStateException("AuditSeverity is required");
            }
            if(id == null) {
                id = AuditlogId.newId();
            }
            return new Auditlog(id, userIdentifier, severity);
        }
    }
     

}
