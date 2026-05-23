package org.example.spanishbanksquarterinfoapi.operation;

import org.example.spanishbanksquarterinfoapi.declaration.Declaration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(OperationController.class)
@AutoConfigureRestTestClient
public class OperationControllerTest {
    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private OperationService operationService;

    @Test
    void shouldReturnAnOperation() throws Exception {
        Operation operation = new Operation();
        operation.setId(1L);
        operation.setType("A. Operaciones de activo");
        Declaration declaration = new Declaration();
        declaration.setId(1L);
        operation.setDeclaration(declaration);

        when(operationService.findByDeclarationIdAndId(1L, 1L)).thenReturn(Optional.of(operation));

        restTestClient.get().uri("/api/declarations/1/operations/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.type").isEqualTo("A. Operaciones de activo");
    }

    @Test
    void shouldNotReturnAnOperationNotFound() throws  Exception {
        when(operationService.findByDeclarationIdAndId(2L, 1L)).thenReturn(Optional.empty());

        restTestClient.get().uri("/api/declarations/1/operations/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}
