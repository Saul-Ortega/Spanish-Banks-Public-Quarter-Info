package org.example.spanishbanksquarterinfoapi.declaration;

import org.example.spanishbanksquarterinfoapi.bank.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.sql.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(DeclarationController.class)
@AutoConfigureRestTestClient
public class DeclarationControllerTest {
    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private DeclarationService declarationService;

    @Test
    void shouldReturnADeclarationWhenFound() throws Exception {
        Declaration declaration = new Declaration();
        declaration.setId(1L);
        declaration.setQuarter("2026/1");
        declaration.setType("TRIMESTRAL");
        declaration.setDeclarationDate(Date.valueOf("2026-03-30"));
        declaration.setPublishedDate(Date.valueOf("2026-04-01"));
        Bank bank = new Bank();
        bank.setId(1L);
        declaration.setBank(bank);

        when(declarationService.findByBankIdAndId(1L, 1L)).thenReturn(Optional.of(declaration));

        restTestClient.get().uri("/api/banks/1/declarations/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.quarter").isEqualTo("2026/1");
    }

    @Test
    void shouldNotReturnADeclarationNotFound() throws Exception {
        when(declarationService.findByBankIdAndId(2L, 1L)).thenReturn(Optional.empty());

        restTestClient.get().uri("/api/banks/2/declarations/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}
