package sop.local.auditlog.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sop.local.auditlog.application.api.AuditlogDirectory;
import sop.local.auditlog.application.api.dto.AuditlogResponse;
import sop.local.auditlog.application.api.dto.CreateAuditlogCmd;
import sop.local.auditlog.application.api.dto.CreatedAuditlogResult;
import sop.local.auditlog.application.api.dto.ReadAuditlogByIdQuery;
import sop.local.auditlog.domain.model.Auditlog;
import sop.local.auditlog.domain.model.valueobjects.AuditlogId;
import sop.local.auditlog.domain.model.valueobjects.UserIdentifier;
import sop.local.auditlog.domain.ports.out.AuditlogRepositoryPort;
import sop.local.auditlog.domain.service.AuditlogDomain;
import sop.local.enums.AuditSeverity;

/**
 * Use case service
 * 
 * outer ring but calls inner domain
 */

@Service
public class AuditlogApplicationService implements AuditlogDirectory {

    private final AuditlogDomain domain;
    private final AuditlogRepositoryPort repository;

    AuditlogApplicationService(AuditlogDomain domain, @Qualifier("inMemoryAuditlogRepository") AuditlogRepositoryPort repository) {
        this.domain = domain;
        this.repository = repository;
    }

    @Transactional
    @Override
    public CreatedAuditlogResult createAuditlog(CreateAuditlogCmd cmd) {
       Auditlog log = domain.createAuditlog(new UserIdentifier(cmd.userIdentifier()), cmd.severity());
       repository.save(log);
       return new CreatedAuditlogResult(log.getAuditlogId().value());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AuditlogResponse> findById(ReadAuditlogByIdQuery query) {
        Optional<Auditlog> log = repository.findById(new AuditlogId(query.id()));
        if(log.isEmpty()) {
            return Optional.empty();
        }
        AuditlogResponse resp = new AuditlogResponse(
            log.get().getAuditlogId().value(),
            log.get().getUserIdentifier().value(),
            log.get().getSeverity()
        );
        return Optional.ofNullable(resp);
    }


    @Transactional(readOnly = true)
    @Override
    public List<AuditlogResponse> findAll() {
        List<Auditlog> logs = repository.findAll();
        List<AuditlogResponse> responses = new ArrayList<>();
        for(Auditlog log : logs) {
            responses.add(new AuditlogResponse(
                log.getAuditlogId().value(),
                log.getUserIdentifier().value(),
                log.getSeverity()
            ));
        }   
        return responses;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuditlogResponse> findBySearchParams(UUID id, String userIdentifier, AuditSeverity severity) {
        List<Auditlog> logs = repository.findBySearchParams(id, userIdentifier, severity);
        List<AuditlogResponse> responses = new ArrayList<>();
        for(Auditlog log : logs) {
            responses.add(new AuditlogResponse(
                log.getAuditlogId().value(),
                log.getUserIdentifier().value(),
                log.getSeverity()
            ));
        }   
        return responses;
    }

}
