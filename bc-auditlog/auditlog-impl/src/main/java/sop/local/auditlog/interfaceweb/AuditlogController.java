package sop.local.auditlog.interfaceweb;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import sop.local.auditlog.application.api.AuditlogDirectory;
import sop.local.auditlog.application.api.dto.AuditlogResponse;
import sop.local.auditlog.application.api.dto.CreateAuditlogCmd;
import sop.local.auditlog.application.api.dto.CreatedAuditlogResult;
import sop.local.auditlog.application.api.dto.ReadAuditlogByIdQuery;
import sop.local.enums.AuditSeverity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/auditlogs")
public class AuditlogController {

    private final AuditlogDirectory directory;

    AuditlogController(AuditlogDirectory directory) {
        this.directory = directory;
    }

    @PostMapping(consumes="application/json", produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreatedAuditlogResult> create(@RequestBody CreateAuditlogCmd cmd) {
        CreatedAuditlogResult result = directory.createAuditlog(cmd);
        URI location = URI.create("/api/auditlogs/auditlog/"+result.id());
        return ResponseEntity.created(Objects.requireNonNull(location)).body(result);
    }

    /* public GET by path /api/auditlogs/auditlog/{id} */
    @GetMapping(path = "/auditlog/{id}", produces="application/json")
    public ResponseEntity<AuditlogResponse> getByIdPath(@PathVariable("id") UUID id) {
        return directory.findById(new ReadAuditlogByIdQuery(id))
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.noContent().build());
    }


    /* public GET by parameters /api/auditlogs?id=<UUID> */
    @GetMapping(produces="application/json")
    public ResponseEntity<List<AuditlogResponse>> findBySearchParams(
        @RequestParam(name="id", required = false) UUID id,
        @RequestParam(name="user", required = false) String userIdentifier,
        @RequestParam(name="severity", required = false) AuditSeverity severity) 
    {
        if(id == null && userIdentifier == null && severity == null) {
            return ResponseEntity.ok(directory.findAll());
        }
        return ResponseEntity.ok(directory.findBySearchParams(id, userIdentifier, severity));
    
    }

    @PutMapping(produces="application/json")
    public ResponseEntity<String> anonymizResponseEntity(@RequestParam("user") String userIdentifier) {
        //TODO: process PUT request
        int records = directory.setUserlogsToAnonymous(userIdentifier);
        String responseMessage = "Anonymized " + records + " audit log records for user: " + userIdentifier;
        return ResponseEntity.ok(responseMessage);
    }
        
}
    
    /* 
        @GetMapping(produces="application/json", params={"id", "userIdentifier", "severity"})
    public ResponseEntity<List<AuditlogResponse>> getBySearchParams(®RequestParam("id") UUID id) {
        return directory.findById(new ReadAuditlogByIdQuery(id))
            .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());       
    }
            */
