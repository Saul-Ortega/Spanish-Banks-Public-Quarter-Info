package org.example.spanishbanksquarterinfoapi.bank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(BankController.class)
@AutoConfigureRestTestClient
public class BankControllerTest {
    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private BankService bankService;

    @Test
    void shouldReturnABankWhenFound() throws Exception {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setEntity("2100");
        bank.setDenomination("CAIXABANK, S.A.");

        when(bankService.findById(1L)).thenReturn(Optional.of(bank));

        restTestClient.get().uri("/api/banks/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.entity").isEqualTo("2100");
    }

    @Test
    void shouldNotReturnABankWhenNotFound() throws Exception {
        when(bankService.findById(2L)).thenReturn(Optional.empty());

        restTestClient.get().uri("/api/banks/2")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldReturnABankWhenCreated() throws Exception {
        Bank bank = new Bank();
        bank.setEntity("2100");
        bank.setDenomination("CAIXABANK, S.A.");

        when(bankService.createBank(bank)).thenReturn(bank);

        restTestClient.post().uri("/api/banks")
                .body(bank)
                .exchange()
                .expectStatus().isCreated()
                .expectBody();
    }

    @Test
    void shouldReturnABankWhenUpdated() throws Exception {
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setEntity("2100");
        bank.setDenomination("CAIXABANK, S.A.");

        when(bankService.updateBank(bank.getId(), bank)).thenReturn(Optional.of(bank));

        restTestClient.put().uri("/api/banks/1")
                .body(bank)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    @Test
    void shouldNotReturnABankWhenUpdateAndNotExists() throws Exception {
        Bank bank = new Bank();
        bank.setId(2L);

        when(bankService.updateBank(bank.getId(), bank)).thenReturn(Optional.empty());

        restTestClient.put().uri("/api/banks/2")
                .body(bank)
                .exchange()
                .expectStatus().isNotFound();
    }
}
