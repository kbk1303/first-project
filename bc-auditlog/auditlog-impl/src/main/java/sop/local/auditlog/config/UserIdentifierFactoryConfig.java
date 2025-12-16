package sop.local.auditlog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sop.local.auditlog.domain.model.valueobjects.UserIdentifierFactory;

@Configuration
public class UserIdentifierFactoryConfig {
@Bean
    public UserIdentifierFactory userIdentifierFactory(
            UserIdentifierPatternProps props) {
        return new UserIdentifierFactory(props.pattern());
    }   
}
