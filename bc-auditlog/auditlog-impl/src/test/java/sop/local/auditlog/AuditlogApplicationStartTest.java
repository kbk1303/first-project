package sop.local.auditlog;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class AuditlogApplicationStartTest {
@Test
void main_runs_withoutExceptions() {
    String oldWebType   = System.getProperty("spring.main.web-application-type");
    String oldExclude   = System.getProperty("spring.autoconfigure.exclude");

    try {
        System.setProperty("spring.main.web-application-type", "none");
        System.setProperty(
            "spring.autoconfigure.exclude",
            "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
            "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
        );

        assertDoesNotThrow(() -> AuditlogApplicationStart.main(new String[0]));
    } finally {
        restoreProperty("spring.main.web-application-type", oldWebType);
        restoreProperty("spring.autoconfigure.exclude", oldExclude);
    }
}

private void restoreProperty(String key, String oldValue) {
    if (oldValue == null) {
        System.clearProperty(key);
    } else {
        System.setProperty(key, oldValue);
    }
}

}
