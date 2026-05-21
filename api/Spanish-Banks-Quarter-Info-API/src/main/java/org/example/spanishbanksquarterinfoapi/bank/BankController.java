package org.example.spanishbanksquarterinfoapi.bank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/banks")
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity<List<Bank>> findAll() {
        List<Bank> bankList = bankService.findAll();
        return ResponseEntity.ok(bankList);
    }

    @GetMapping("id/{requestedId}")
    public ResponseEntity<Bank> findById(@PathVariable Long requestedId) {
        Optional<Bank> bankOptional = bankService.findById(requestedId);
        return bankOptional.isPresent() ? ResponseEntity.ok(bankOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody Bank newBank) {
        Bank savedBank = bankService.createBank(newBank);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBank);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody Bank updatedBank) {
        Optional<Bank> bankOptional = bankService.updateBank(id, updatedBank);
        return bankOptional.isPresent() ? ResponseEntity.ok(bankOptional.get()) : ResponseEntity.notFound().build();
    }
}
