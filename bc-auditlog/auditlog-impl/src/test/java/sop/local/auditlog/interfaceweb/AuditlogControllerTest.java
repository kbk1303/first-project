package sop.local.auditlog.interfaceweb;


import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import jakarta.annotation.Resource;
import sop.local.auditlog.application.api.AuditlogDirectory;
import sop.local.auditlog.application.api.dto.CreateAuditlogCmd;
import sop.local.auditlog.application.api.dto.CreatedAuditlogResult;

@WebMvcTest(controllers = AuditlogController.class)
@AutoConfigureMockMvc(addFilters = false) 
public class AuditlogControllerTest {

     
    @TestConfiguration
    static class Mocks {
        @Bean
        AuditlogDirectory directory() {
            return Mockito.mock(AuditlogDirectory.class);
        }
    }
    

    @Resource
    AuditlogDirectory directory;

    @Resource
    MockMvc mvc;

    @Test
    void happyPath_createWithValidParameters_shouldCreateNewCreatedAuditlogResult() throws Exception {
        UUID id = UUID.fromString("6a0d3b2e-9c41-4f0b-8c32-1e7f9a4d5b89");
        Mockito.when(directory.createAuditlog(Mockito.any(CreateAuditlogCmd.class))).thenReturn(new CreatedAuditlogResult(id));

        mvc.perform(MockMvcRequestBuilders.post("/api/auditlogs")
            .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
            .content(
                """
                    {
                     "userIdentifier": "KKRI",
                     "severity": "WARNING"
                    }   
                """))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.header().string("Location", "/api/auditlogs/auditlog/"+id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id.toString()));
    }
}
