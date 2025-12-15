package sop.local.auditlog.interfaceadapters.persistence.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sop.local.enums.AuditSeverity;

public interface AuditlogSpringDataRepository extends JpaRepository<AuditlogEntity, java.util.UUID> {
        @Query("SELECT a FROM AuditlogEntity a WHERE " +
           "(:id IS NULL OR a.id = :id) AND " +
           "(:userIdentifier IS NULL OR a.userIdentifier LIKE %:userIdentifier%) AND " +
           "(:severity IS NULL OR a.severity = :severity)")
    List<AuditlogEntity> findBySearchParams(
            @Param("id") UUID id,
            @Param("userIdentifier") String userIdentifier,
            @Param("severity") AuditSeverity severity);

}
