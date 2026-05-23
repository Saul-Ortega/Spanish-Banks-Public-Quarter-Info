package org.example.spanishbanksquarterinfoapi.operation_section;

import org.example.spanishbanksquarterinfoapi.operation.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(OperationSectionController.class)
@AutoConfigureRestTestClient
public class OperationSectionControllerTest {
    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private OperationSectionService operationSectionService;

    @Test
    void shouldReturnAnOperationSection() throws Exception {
        OperationSection operationSection = new OperationSection();
        operationSection.setId(1L);
        operationSection.setSection("A.1 Préstamos Hipotecarios");
        Operation operation = new Operation();
        operation.setId(1L);
        operationSection.setOperation(operation);

        when(operationSectionService.findByOperationIdAndId(1L, 1L)).thenReturn(Optional.of(operationSection));

        restTestClient.get().uri("/api/operations/1/operation-sections/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.section").isEqualTo("A.1 Préstamos Hipotecarios");
    }

    @Test
    void shouldNotReturnAnOperationSectionNotFound() throws Exception {
        when(operationSectionService.findByOperationIdAndId(2L, 1L)).thenReturn(Optional.empty());

        restTestClient.get().uri("/api/operations/2/operation-sections/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}
