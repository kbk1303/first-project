package sop.local.auditlog.architecture;

import org.springframework.stereotype.Component;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import sop.local.auditlog.domain.model.valueobjects.UserIdentifier;
import sop.local.auditlog.domain.model.valueobjects.UserIdentifierFactory;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "sop.local.auditlog",
    importOptions = {
        ImportOption.DoNotIncludeTests.class,   // <-- udelukker target/test-classes
    })  
public class UserIdentifierFactoryArchTest {


    @ArchTest
    static void userIdentifierFactory_is_a_spring_component(JavaClasses importedClasses) {
        ArchRule rule = classes()
            .that().resideInAPackage("..domain.model.valueobjects..")
            .and().haveSimpleName("UserIdentifierFactory")
            .should().beAnnotatedWith(Component.class)
            .because("UserIdentifierFactory should be a Spring component to allow dependency injection.");

        rule.check(importedClasses);
    }

    @ArchTest
    static void only_user_identifier_factory_may_instantiate_UserIdentifier(JavaClasses importedClasses) {
        ArchRule rule = noClasses()
            .that().resideInAnyPackage("..sop.local.auditlog..")
            .and()
            .doNotHaveFullyQualifiedName(UserIdentifierFactory.class.getName())
            .should()
            .callConstructor(UserIdentifier.class, String.class)
            .because("Only UserIdentifierFactory must construct UserIdentifier to enforce the invariant centrally.");

        rule.check(importedClasses);
    }
            
}
