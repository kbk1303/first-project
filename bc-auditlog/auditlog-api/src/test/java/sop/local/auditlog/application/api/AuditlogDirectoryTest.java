package sop.local.auditlog.application.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import sop.local.auditlog.application.api.dto.AuditlogResponse;
import sop.local.auditlog.application.api.dto.CreateAuditlogCmd;
import sop.local.auditlog.application.api.dto.CreatedAuditlogResult;
import sop.local.auditlog.application.api.dto.ReadAuditlogByIdQuery;
import sop.local.auditlog.application.api.dto.ReadAuditlogBySeverityQuery;
import sop.local.auditlog.application.api.dto.ReadAuditlogByUserIdentifierQuery;
import sop.local.enums.AuditSeverity;

public class AuditlogDirectoryTest {

    private AuditlogDirectory directory; 

    @BeforeEach
    public void setup() {
        directory = Mockito.mock(AuditlogDirectory.class);
    }

    /****************** Happypath *********************************/

    @Test
    public void happyPath_createNewAuditlog_shouldCreateNewInstance() {
        UUID id = UUID.randomUUID();
        Mockito.when(directory.createAuditlog(Mockito.any(CreateAuditlogCmd.class))).thenReturn(new CreatedAuditlogResult(id));

        CreatedAuditlogResult result = directory.createAuditlog(new CreateAuditlogCmd("KKRI", AuditSeverity.INFO));

        assertNotNull(result);
        assertEquals(id, result.id());
        Mockito.verify(directory, Mockito.times(1)).createAuditlog(Mockito.any(CreateAuditlogCmd.class));
        Mockito.verifyNoMoreInteractions(directory);

    }

    @Test
    public void happyPath_ReadById_shouldReturnInstance() {
        Optional<AuditlogResponse> resp = Optional.ofNullable(new AuditlogResponse(UUID.randomUUID(), "KKRI", AuditSeverity.INFO));
        Mockito.when(directory.findById(Mockito.any(ReadAuditlogByIdQuery.class))).thenReturn(resp);

        Optional<AuditlogResponse> result = directory.findById(new ReadAuditlogByIdQuery(UUID.randomUUID()));
        assertNotNull(result);
        assertEquals("KKRI", result.get().userIdentifier());
        assertEquals(AuditSeverity.INFO, result.get().severity());
        assertEquals(resp.get().id(), result.get().id());
        Mockito.verify(directory, Mockito.times(1)).findById(Mockito.any(ReadAuditlogByIdQuery.class));
        Mockito.verifyNoMoreInteractions(directory);
    }

    @Test
    public void happyPath_readAll_shouldReturnListOfInstances() {
        List<AuditlogResponse> resps = List.of(
                new AuditlogResponse(UUID.randomUUID(), "KKRI", AuditSeverity.INFO),
                new AuditlogResponse(UUID.randomUUID(), "INESA", AuditSeverity.WARNING),
                new AuditlogResponse(UUID.randomUUID(), "BENJ0568", AuditSeverity.ERROR),
                new AuditlogResponse(UUID.randomUUID(), "NOAH007R", AuditSeverity.INFO));
    
        Mockito.when(directory.findAll()).thenReturn(resps);

        List<AuditlogResponse> results = directory.findAll();
        assertNotNull(results);
        assertEquals(4, results.size());
        assertEquals(resps.get(0), results.get(0));
        Mockito.verify(directory, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(directory);

    }


    @Test
    public void happyPath_ReadByIdNull_shouldReturnNull() {
        Mockito.when(directory.findById(Mockito.any())).thenReturn(Optional.empty());

        Optional<AuditlogResponse> result = directory.findById(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertFalse(result.isPresent());
        Mockito.verify(directory, Mockito.times(1)).findById(null);
        Mockito.verifyNoMoreInteractions(directory);
    }

    @Test
    public void unhappyPath_ReadBySeverityNotFound_shouldThrowException() {
        Mockito.when(directory.findByAuditSeverity(Mockito.any(ReadAuditlogBySeverityQuery.class))).thenAnswer(inv -> {
            var msg = "No enum constant sop.local.enums.AuditSeverity."+inv.getArgument(0);
            throw new IllegalArgumentException(msg);
        });
        var ex = assertThrows(IllegalArgumentException.class, () -> directory.findByAuditSeverity(new ReadAuditlogBySeverityQuery(AuditSeverity.valueOf("NOT FOUND"))));
        assertEquals(IllegalArgumentException.class, ex.getClass());
        assertEquals("No enum constant sop.local.enums.AuditSeverity.NOT FOUND", ex.getMessage());
        /* not verified since the Enum.valueOf is fired before the call and throws the IllegalArgumentException */
        //Mockito.verify(directory, Mockito.times(0)).findByAuditSeverity(new ReadAuditlogBySeverityQuery(AuditSeverity.valueOf("NOT FOUND")));
        Mockito.verifyNoMoreInteractions(directory);
    }

    @Test
    public void happyPath_ReadByUserIdentitityNotFound_shouldReturnEmptyList() {
        List<AuditlogResponse> resp = List.of();
        Mockito.when(directory.findByUserIdentifier(Mockito.any(ReadAuditlogByUserIdentifierQuery.class))).thenReturn(resp);
        List<AuditlogResponse> result  = directory.findByUserIdentifier(new ReadAuditlogByUserIdentifierQuery("KRIFDFKDERER"));
        assertNotNull(result);
        assertTrue(result.isEmpty());
        Mockito.verify(directory, Mockito.times(1)).findByUserIdentifier(new ReadAuditlogByUserIdentifierQuery("KRIFDFKDERER"));
        Mockito.verifyNoMoreInteractions(directory);

    }

}
