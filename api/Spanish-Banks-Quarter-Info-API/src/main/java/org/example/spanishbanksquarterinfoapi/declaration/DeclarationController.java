package org.example.spanishbanksquarterinfoapi.declaration;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/banks/{bankId}/declarations")
public class DeclarationController {
    private final DeclarationService declarationService;

    public DeclarationController(DeclarationService declarationService) {
        this.declarationService = declarationService;
    }

    @GetMapping
    public ResponseEntity<List<Declaration>> findByBankId(@PathVariable Long bankId) {
        List<Declaration> declarationList = declarationService.findByBankId(bankId);
        return ResponseEntity.ok(declarationList);
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Declaration> findById(@PathVariable Long bankId, @PathVariable Long requestedId) {
        Optional<Declaration> declarationOptional = declarationService.findByBankIdAndId(bankId, requestedId);
        return declarationOptional.isPresent() ? ResponseEntity.ok(declarationOptional.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping(params = "quarter")
    public ResponseEntity<Declaration> findByQuarter(@RequestParam String quarter) {
        Optional<Declaration> declarationOptional = declarationService.findByQuarter(quarter);
        return declarationOptional.isPresent() ? ResponseEntity.ok(declarationOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Declaration> createDeclaration(@PathVariable Long bankId, @Valid @RequestBody Declaration newDeclaration) {
        Declaration savedDeclaration = declarationService.createDeclaration(bankId, newDeclaration);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDeclaration);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Declaration> updateDeclaration(@PathVariable Long bankId, @PathVariable Long id, @Valid @RequestBody Declaration updatedDeclaration) {
        Optional<Declaration> declarationOptional = declarationService.updateDeclaration(bankId, id, updatedDeclaration);
        return declarationOptional.isPresent() ? ResponseEntity.ok(declarationOptional.get()) : ResponseEntity.notFound().build();
    }
}
