package sop.local.auditlog.interfaceadapters.persistence.jpa;

import org.springframework.stereotype.Component;

import sop.local.auditlog.domain.model.Auditlog;
import sop.local.auditlog.domain.model.valueobjects.AuditlogId;
import sop.local.auditlog.domain.model.valueobjects.UserIdentifierFactory;

@Component
public class AuditlogJpaMapper {
    
    private final UserIdentifierFactory userIdentifierFactory;
    
    public AuditlogJpaMapper(UserIdentifierFactory userIdentifierFactory) {
        this.userIdentifierFactory = userIdentifierFactory;
    }

    /* JPA -> Domain */
    public Auditlog toDomain(AuditlogEntity entity) {
        if(entity.getId() == null) {
            return Auditlog.builder()
                .userIdentifier(userIdentifierFactory.create(entity.getUserIdentifier()))
                .severity(entity.getSeverity())
                .build();
        }
        return Auditlog.builder().id(new AuditlogId(entity.getId()))
                .userIdentifier(userIdentifierFactory.create(entity.getUserIdentifier()))
                .severity(entity.getSeverity())
                .build();
    }

    /* Domain -> JPA (new/existing)*/
    public AuditlogEntity toEntity(Auditlog auditlog) {
        return new AuditlogEntity(
            auditlog.getAuditlogId().value(),
            auditlog.getUserIdentifier().value(),
            auditlog.getSeverity()
        );
    }

    /* Domain -> existing JPA(update) */
    public void toExistingEntity(Auditlog auditlog, AuditlogEntity entity) {
        entity.setUserIdentifier(auditlog.getUserIdentifier().value());
        entity.setSeverity(auditlog.getSeverity());
    }
}
