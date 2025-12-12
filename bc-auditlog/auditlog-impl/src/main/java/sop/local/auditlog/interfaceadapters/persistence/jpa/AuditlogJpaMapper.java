package sop.local.auditlog.interfaceadapters.persistence.jpa;

import sop.local.auditlog.domain.model.Auditlog;
import sop.local.auditlog.domain.model.valueobjects.AuditlogId;
import sop.local.auditlog.domain.model.valueobjects.UserIdentifier;

public class AuditlogJpaMapper {
    private AuditlogJpaMapper() {}

    /* JPA -> Domain */
    public static  Auditlog toDomain(AuditlogEntity entity) {
        if(entity.getId() == null) {
            return Auditlog.builder()
                .userIdentifier(new UserIdentifier(entity.getUserIdentifier()))
                .severity(entity.getSeverity())
                .build();
        }
        return Auditlog.builder().id(new AuditlogId(entity.getId()))
                .userIdentifier(new UserIdentifier(entity.getUserIdentifier()))
                .severity(entity.getSeverity())
                .build();
    }

    /* Domain -> JPA (new/existing)*/
    public static AuditlogEntity toEntity(Auditlog auditlog) {
        return new AuditlogEntity(
            auditlog.getAuditlogId().value(),
            auditlog.getUserIdentifier().value(),
            auditlog.getSeverity()
        );
    }

    /* Domain -> existing JPA(update) */
    public static void toExistingEntity(Auditlog auditlog, AuditlogEntity entity) {
        entity.setUserIdentifier(auditlog.getUserIdentifier().value());
        entity.setSeverity(auditlog.getSeverity());
    }
}
