package sop.local.enums;
import com.fasterxml.jackson.annotation.JsonCreator;


public enum AuditSeverity {
    INFO, WARNING, ERROR;

    
    
    public static AuditSeverity fromString(String value) {
        return EnumUtils.parseEnum(AuditSeverity.class, value);
    }
    
    @JsonCreator
    public static AuditSeverity fromJson(String value) {
        return fromString(value);
    }
}
