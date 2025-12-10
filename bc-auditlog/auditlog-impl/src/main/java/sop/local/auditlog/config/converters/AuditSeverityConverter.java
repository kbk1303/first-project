package sop.local.auditlog.config.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sop.local.enums.*;

@Component
public class AuditSeverityConverter implements Converter<String, AuditSeverity> {
    @Override
    public AuditSeverity convert(String source) {
        return EnumUtils.parseEnum(AuditSeverity.class, source);
    }
}