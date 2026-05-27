package org.example.spanishbanksquarterinfoapi.declaration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/banks/{bankId}/declarations")
@Tag(name = "declaration", description = "Create, read and update declarations")
public class DeclarationController {
    private final DeclarationService declarationService;

    public DeclarationController(DeclarationService declarationService) {
        this.declarationService = declarationService;
    }

    @GetMapping
    @Operation(summary = "Gets all declarations")
    @ApiResponse(responseCode = "200", description = "Returns all declarations successfully")
    public ResponseEntity<List<Declaration>> findByBankId(@Parameter(description = "Bank ID", required = true) @PathVariable Long bankId) {
        List<Declaration> declarationList = declarationService.findByBankId(bankId);
        return ResponseEntity.ok(declarationList);
    }

    @GetMapping("/{requestedId}")
    @Operation(summary = "Gets a declaration by ID")
    @ApiResponse(responseCode = "200", description = "Returns the declaration successfully")
    @ApiResponse(responseCode = "404", description = "Returns declaration not found")
    public ResponseEntity<Declaration> findById(
            @Parameter(description = "Bank ID", required = true) @PathVariable Long bankId,
            @Parameter(description = "Declaration ID", required = true) @PathVariable Long requestedId
    ) {
        Optional<Declaration> declarationOptional = declarationService.findByBankIdAndId(bankId, requestedId);
        return declarationOptional.isPresent() ? ResponseEntity.ok(declarationOptional.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping(params = "quarter")
    @Operation(summary = "Gets a declaration by Quarter")
    @ApiResponse(responseCode = "200", description = "Returns the declaration successfully")
    @ApiResponse(responseCode = "404", description = "Returns declaration not found")
    public ResponseEntity<Declaration> findByQuarter(@Parameter(description = "Requested Quarter", required = true) @RequestParam String quarter) {
        Optional<Declaration> declarationOptional = declarationService.findByQuarter(quarter);
        return declarationOptional.isPresent() ? ResponseEntity.ok(declarationOptional.get()) : ResponseEntity.notFound().build();
    }


    @PostMapping
    @Operation(summary = "Creates a declaration")
    @ApiResponse(responseCode = "201", description = "Creates the declaration successfully")
    public ResponseEntity<Declaration> createDeclaration(
            @Parameter(description = "Bank ID", required = true) @PathVariable Long bankId,
            @Parameter(description = "Declaration Object", required = true) @Valid @RequestBody Declaration newDeclaration
    ) {
        Declaration savedDeclaration = declarationService.createDeclaration(bankId, newDeclaration);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDeclaration);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a declaration by ID")
    @ApiResponse(responseCode = "200", description = "Updates the declaration successfully")
    @ApiResponse(responseCode = "404", description = "Does not update a declaration not found")
    public ResponseEntity<Declaration> updateDeclaration(
            @Parameter(description = "Bank ID", required = true) @PathVariable Long bankId,
            @Parameter(description = "Declaration ID") @PathVariable Long id,
            @Parameter(description = "Declaration Object") @Valid @RequestBody Declaration updatedDeclaration
    ) {
        Optional<Declaration> declarationOptional = declarationService.updateDeclaration(bankId, id, updatedDeclaration);
        return declarationOptional.isPresent() ? ResponseEntity.ok(declarationOptional.get()) : ResponseEntity.notFound().build();
    }
}
