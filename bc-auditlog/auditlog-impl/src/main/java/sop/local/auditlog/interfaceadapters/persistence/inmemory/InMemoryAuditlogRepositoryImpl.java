package sop.local.auditlog.interfaceadapters.persistence.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import sop.local.auditlog.domain.model.Auditlog;
import sop.local.auditlog.domain.model.valueobjects.AuditlogId;
import sop.local.auditlog.domain.ports.out.AuditlogRepositoryPort;
import sop.local.enums.AuditSeverity;

@Repository
@Qualifier("inMemoryAuditlogRepository")
public class InMemoryAuditlogRepositoryImpl implements AuditlogRepositoryPort {

    private final List<Auditlog> auditlogs;
    
    public InMemoryAuditlogRepositoryImpl()  {
        this.auditlogs = new ArrayList<>(List.of(
            /* 
            Auditlog.builder().id(new AuditlogId(UUID.randomUUID())).userIdentifier(new UserIdentifier("KKRI")).severity(AuditSeverity.INFO).build(),
            Auditlog.builder().id(new AuditlogId(UUID.randomUUID())).userIdentifier(new UserIdentifier("INESA")).severity(AuditSeverity.WARNING).build(),
            Auditlog.builder().id(new AuditlogId(UUID.randomUUID())).userIdentifier(new UserIdentifier("BENJ0568")).severity(AuditSeverity.ERROR).build(),
            Auditlog.builder().id(new AuditlogId(UUID.randomUUID())).userIdentifier(new UserIdentifier("NOAH007R")).severity(AuditSeverity.INFO).build(),
            Auditlog.builder().id(new AuditlogId(UUID.randomUUID())).userIdentifier(new UserIdentifier("KKRI")).severity(AuditSeverity.ERROR).build(),
            Auditlog.builder().id(new AuditlogId(UUID.randomUUID())).userIdentifier(new UserIdentifier("BENJ0867F")).severity(AuditSeverity.INFO).build(),
            Auditlog.builder().id(new AuditlogId(UUID.randomUUID())).userIdentifier(new UserIdentifier("INESA")).severity(AuditSeverity.WARNING).build(),
            Auditlog.builder().id(new AuditlogId(UUID.randomUUID())).userIdentifier(new UserIdentifier("ARNE")).severity(AuditSeverity.INFO).build(),
            Auditlog.builder().id(new AuditlogId(UUID.randomUUID())).userIdentifier(new UserIdentifier("KRIK4343")).severity(AuditSeverity.INFO).build(),
            Auditlog.builder().id(new AuditlogId(UUID.randomUUID())).userIdentifier(new UserIdentifier("REREWW222")).severity(AuditSeverity.WARNING).build(),
            Auditlog.builder().id(new AuditlogId(UUID.randomUUID())).userIdentifier(new UserIdentifier("KKRI")).severity(AuditSeverity.ERROR).build()
            */
            ));
    }

    @Override
    public Auditlog save(Auditlog auditlog) {
        auditlogs.add(auditlog);
        return auditlog;
    }

    @Override
    public List<Auditlog> findBySearchParams(UUID id, String userIdentifier, AuditSeverity severity) {
        var results = auditlogs.stream()
                .filter(a -> (id == null || a.getAuditlogId().value().equals(id)) &&
                             (userIdentifier == null || a.getUserIdentifier().value().equals(userIdentifier)) &&
                             (severity == null || a.getSeverity().equals(severity)))
                .toList();
        return results;
    }

    @Override
    public List<Auditlog> findAll() {
        return auditlogs.stream().toList();
    }

    @Override
    public Optional<Auditlog> findById(AuditlogId auditlogId) {
        var log =  auditlogs.stream()
                .filter(a -> a.getAuditlogId().equals(auditlogId))
                .findFirst();
        return (!log.isEmpty()) ? log : Optional.empty();
    }
    

}
