package sop.local.auditlog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import sop.local.auditlog.domain.model.valueobjects.UserIdentifierFactory;

@Component
public class UserIdentifierPatternConfig {

    @Bean
    UserIdentifierFactory userIdentifierFactory(@Value("${organization.login.pattern}") String patternString)  {
        return new UserIdentifierFactory(patternString);
    }
}
