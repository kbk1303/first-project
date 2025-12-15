package sop.local.auditlog.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "sop.local.auditlog",
    importOptions = {
        ImportOption.DoNotIncludeTests.class,   // <-- udelukker target/test-classes
    })  
public class OnionArchitectureTest {

    @ArchTest
    void hexagonal_architecture(JavaClasses importedClasses) {
            onionArchitecture()
                .domainModels("..domain.model", "..domain.model.valueobjects")
                .applicationServices("..application.service", "..application.api", "..application.api.dto")
                .domainServices("..domain.service", "..domain.ports.out..")
                .adapter("config", "..config", "..config.security..")
                .adapter("interfaceweb", "..interfaceweb..")
                .adapter("persistence", "..interfaceadapters.persistence.jpa..", "..interfaceadapters.persistence.inmemory..")
                .adapter("security", "..security..")
                .withOptionalLayers(true)
                .because("The Auditlog BC must follow the Hexagonal Architecture pattern.")
                .check(importedClasses);
    }
}
