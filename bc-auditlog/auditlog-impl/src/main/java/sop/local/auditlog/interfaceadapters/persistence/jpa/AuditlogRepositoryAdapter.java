package sop.local.auditlog.interfaceadapters.persistence.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import sop.local.auditlog.domain.model.Auditlog;
import sop.local.auditlog.domain.model.valueobjects.AuditlogId;
import sop.local.auditlog.domain.ports.out.AuditlogRepositoryPort;
import sop.local.enums.AuditSeverity;

@Repository
@Qualifier("jpaAuditlogRepository")

public class AuditlogRepositoryAdapter implements AuditlogRepositoryPort {

    private final AuditlogSpringDataRepository jpaRepository;

    public AuditlogRepositoryAdapter(@Autowired AuditlogSpringDataRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Auditlog save(Auditlog auditlog) {
        AuditlogEntity entity = AuditlogJpaMapper.toEntity(auditlog);
        AuditlogEntity savedEntity = jpaRepository.save(entity);
        return AuditlogJpaMapper.toDomain(savedEntity);
    }

    @Override
    public List<Auditlog> findBySearchParams(UUID id, String userIdentifier, AuditSeverity severity) {
        List<AuditlogEntity> entities = jpaRepository.findBySearchParams(id, userIdentifier, severity);
        return entities.stream()
                .map(AuditlogJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Auditlog> findAll() {
        List<AuditlogEntity> entities = jpaRepository.findAll();
        return entities.stream()
                .map(AuditlogJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Auditlog> findById(AuditlogId auditlogId) {
        Optional<AuditlogEntity> entityOpt = jpaRepository.findById(auditlogId.value());
        return entityOpt.map(AuditlogJpaMapper::toDomain);
    }

}
