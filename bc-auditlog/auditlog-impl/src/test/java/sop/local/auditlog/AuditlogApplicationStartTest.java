package sop.local.auditlog;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    classes = AuditlogApplicationStart.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = {
        "spring.main.web-application-type=none",
        "spring.profiles.active=test",
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
    }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuditlogApplicationStartTest {
    
    @Test
    void context_loads_withoutExceptions() {
        // SpringBootTest håndterer alt korrekt
        assertTrue(true);
    }
    
    @Test  
    void main_method_works() {
        // Test med virkelig H2 db
        assertDoesNotThrow(() -> AuditlogApplicationStart.main(new String[0]));
    }
}
