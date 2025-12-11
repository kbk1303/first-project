package sop.local.auditlog.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import sop.local.auditlog.application.api.AuditlogDirectory;
import sop.local.auditlog.application.api.dto.AuditlogResponse;
import sop.local.auditlog.application.api.dto.CreateAuditlogCmd;
import sop.local.auditlog.application.api.dto.CreatedAuditlogResult;
import sop.local.auditlog.application.api.dto.ReadAuditlogByIdQuery;
import sop.local.auditlog.application.api.dto.ReadAuditlogBySeverityQuery;
import sop.local.auditlog.application.api.dto.ReadAuditlogByUserIdentifierQuery;
import sop.local.auditlog.domain.model.Auditlog;
import sop.local.auditlog.domain.model.valueobjects.UserIdentifier;
import sop.local.auditlog.domain.service.AuditlogDomain;
import sop.local.enums.AuditSeverity;

/**
 * Use case service
 * 
 * outer ring but calls inntrer domain
 */

@Service
public class AuditlogApplicationService implements AuditlogDirectory {

    private final AuditlogDomain domain;
    private final List<AuditlogResponse> resps;

    AuditlogApplicationService(AuditlogDomain domain) {
        this.domain = domain;
        resps = new ArrayList<>(List.of(
                new AuditlogResponse(UUID.randomUUID(), "KKRI", AuditSeverity.INFO),
                new AuditlogResponse(UUID.randomUUID(), "INESA", AuditSeverity.WARNING),
                new AuditlogResponse(UUID.randomUUID(), "BENJ0568", AuditSeverity.ERROR),
                new AuditlogResponse(UUID.randomUUID(), "NOAH007R", AuditSeverity.INFO),
                new AuditlogResponse(UUID.randomUUID(), "KKRI", AuditSeverity.ERROR),
                new AuditlogResponse(UUID.randomUUID(), "BENJ0867F", AuditSeverity.INFO),
                new AuditlogResponse(UUID.randomUUID(), "INESA", AuditSeverity.WARNING),
                new AuditlogResponse(UUID.randomUUID(), "ARNE", AuditSeverity.INFO),
                new AuditlogResponse(UUID.randomUUID(), "KRIK4343", AuditSeverity.INFO),
                new AuditlogResponse(UUID.randomUUID(), "REREWW222", AuditSeverity.WARNING),
                new AuditlogResponse(UUID.randomUUID(), "KKRI", AuditSeverity.ERROR),
                new AuditlogResponse(UUID.randomUUID(), "OLE3425D", AuditSeverity.INFO)));
    }

    @Override
    public CreatedAuditlogResult createAuditlog(CreateAuditlogCmd cmd) {
       Auditlog log = domain.createAuditlog(new UserIdentifier(cmd.userIdentifier()), cmd.severity());
       //System.out.println("Created auditlog: " + log.getAuditlogId().value() + ", userIdentifier: " + log.getUserIdentifier().value() + ", severity: " + log.getSeverity());
       resps.add(new AuditlogResponse(log.getAuditlogId().value(), log.getUserIdentifier().value(), log.getSeverity()));

       /* @TODO persistence missing*/

       return new CreatedAuditlogResult(log.getAuditlogId().value());
    }

    @Override
    public Optional<AuditlogResponse> findById(ReadAuditlogByIdQuery query) {

        /* @TODO persistence should take over */
        Optional<AuditlogResponse> resp = findAll().stream()
            .filter(r -> r.id().equals(query.id()))
            .findFirst();
        return (!resp.isEmpty() && query.id().equals(resp.get().id())) ?  resp: Optional.empty();
    }

    @Override
    public List<AuditlogResponse> findByUserIdentifier(ReadAuditlogByUserIdentifierQuery query) {
        
        /* @TODO persistence should take over */
                
        return findAll().stream().filter(r -> r.userIdentifier().equals(query.userIdentifier())).toList();

    }

    @Override
    public List<AuditlogResponse> findByAuditSeverity(ReadAuditlogBySeverityQuery query) {

        /* @TODO persistence should take over */
               
        return findAll().stream().filter(r -> r.severity().equals(query.severity())).toList();
    }

    @Override
    public List<AuditlogResponse> findAll() {
            /* @TODO persistence should take over */
            return resps;
    }

    @Override
    public List<AuditlogResponse> findBySearchParams(UUID id, String userIdentifier, AuditSeverity severity) {
        List<AuditlogResponse> allLogs = findAll();
        if(id != null) {
            return allLogs.stream().filter(r -> r.id().equals(id)).toList();
        }
        if(userIdentifier != null) {
            allLogs =  allLogs.stream().filter(r -> r.userIdentifier().equals(userIdentifier)).toList();

        }   
        if(severity != null) {
            allLogs = allLogs.stream().filter(r -> r.severity().equals(severity)).toList();
        }
        return allLogs;
    }

}
