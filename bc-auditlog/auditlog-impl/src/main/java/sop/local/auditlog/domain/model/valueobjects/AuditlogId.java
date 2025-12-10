package sop.local.auditlog.domain.model.valueobjects;

import java.util.UUID;

import sop.local.valueobjects.DomainId;
import sop.local.valueobjects.utils.UUIDUtil;

public record AuditlogId(UUID value) implements DomainId{

    public AuditlogId {
        value = UUIDUtil.require(value);
    }

    public static AuditlogId newId() {
        return new AuditlogId(UUIDUtil.newUuid());
    }   

    public static AuditlogId of(String uuidStr) {
        return new AuditlogId(UUIDUtil.parseRequired(uuidStr));
    }

    public static AuditlogId fromString(String uuidStr) {
        return of(uuidStr);
    }

}
