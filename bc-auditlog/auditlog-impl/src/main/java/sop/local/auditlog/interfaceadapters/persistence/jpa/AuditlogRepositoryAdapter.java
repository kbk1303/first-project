package sop.local.auditlog.interfaceadapters.persistence.jpa;

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
@Qualifier("jpaAuditlogRepository")

public class AuditlogRepositoryAdapter implements AuditlogRepositoryPort {

    
    private final AuditlogJpaMapper mapper;
    private final AuditlogSpringDataRepository jpaRepository;

    public AuditlogRepositoryAdapter(AuditlogJpaMapper mapper, AuditlogSpringDataRepository jpaRepository) {
        this.mapper = mapper;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Auditlog save(Auditlog auditlog) {
        AuditlogEntity entity = mapper.toEntity(auditlog);
        AuditlogEntity savedEntity = jpaRepository.save(entity);
        savedEntity = jpaRepository.findById(savedEntity.getId()).orElseThrow();
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<Auditlog> findBySearchParams(UUID id, String userIdentifier, AuditSeverity severity) {
        List<AuditlogEntity> entities = jpaRepository.findBySearchParams(id, userIdentifier, severity);
        return entities.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Auditlog> findAll() {
        return findBySearchParams(null, null, null);
    }

    @Override
    public Optional<Auditlog> findById(AuditlogId auditlogId) {
        return Optional.ofNullable(findBySearchParams(auditlogId.value(), null, null).stream()            
                .filter(a -> a.getAuditlogId().equals(auditlogId)).findFirst()
                .orElse(null));
    }

    @Override
    public int anonymizeUserForLogs(List<UUID> ids) {
        return jpaRepository.anonymizeUserForLogs(ids);
    }

}
