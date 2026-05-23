package org.example.spanishbanksquarterinfoapi.operation_subsection;

import org.example.spanishbanksquarterinfoapi.operation_section.OperationSection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(OperationSubsectionController.class)
@AutoConfigureRestTestClient
public class OperationSubsectionControllerTest {
    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private OperationSubsectionService operationSubsectionService;

    @Test
    void shouldReturnAnOperationSubsection() throws Exception {
        OperationSubsection operationSubsection = new OperationSubsection();
        operationSubsection.setId(1L);
        operationSubsection.setPracticed(true);
        Map<String, Object> data = new HashMap<>();
        data.put("TAE(%)", 1.86);
        operationSubsection.setData(data);
        OperationSection operationSection = new OperationSection();
        operationSection.setId(1L);
        operationSubsection.setOperationSection(operationSection);

        when(operationSubsectionService.findByOperationSectionIdAndId(1L, 1L)).thenReturn(Optional.of(operationSubsection));

        restTestClient.get().uri("/api/operation-sections/1/operation-subsections/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.practiced").isEqualTo(true);
    }

    @Test
    void shouldNotReturnAnOperationSectionNotFound() throws Exception {
        when(operationSubsectionService.findByOperationSectionIdAndId(2L, 1L)).thenReturn(Optional.empty());

        restTestClient.get().uri("/api/operation-sections/2/operation-subsections/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}
