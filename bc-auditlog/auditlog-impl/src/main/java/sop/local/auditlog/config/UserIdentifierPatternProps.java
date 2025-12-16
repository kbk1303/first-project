package sop.local.auditlog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "organization.login")
public record UserIdentifierPatternProps(String pattern) {}
