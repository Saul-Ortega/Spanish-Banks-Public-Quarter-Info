package org.example.spanishbanksquarterinfoapi.bank;

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
@RequestMapping("/api/banks")
@Tag(name = "bank", description = "Create, read and update banks")
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    @Operation(summary = "Gets all banks")
    @ApiResponse(responseCode = "200", description = "Returns all banks successfully")
    public ResponseEntity<List<Bank>> findAll() {
        List<Bank> bankList = bankService.findAll();
        return ResponseEntity.ok(bankList);
    }

    @GetMapping("/{requestedId}")
    @Operation(summary = "Gets a bank by ID")
    @ApiResponse(responseCode = "200", description = "Returns the bank successfully")
    @ApiResponse(responseCode = "404", description = "Returns bank not found")
    public ResponseEntity<Bank> findById(@Parameter(description = "Bank ID", required = true) @PathVariable Long requestedId) {
        Optional<Bank> bankOptional = bankService.findById(requestedId);
        return bankOptional.isPresent() ? ResponseEntity.ok(bankOptional.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping(params = "denomination")
    @Operation(summary = "Gets a bank by Denomination")
    @ApiResponse(responseCode = "200", description = "Returns the bank successfully")
    @ApiResponse(responseCode = "404", description = "Returns bank not found")
    public ResponseEntity<Bank> findByDenomination(@Parameter(description = "Requested Denomination", required = true) @RequestParam String denomination) {
        Optional<Bank> bankOptional = bankService.findByDenomination(denomination);
        return bankOptional.isPresent() ? ResponseEntity.ok(bankOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Creates a bank")
    @ApiResponse(responseCode = "201", description = "Creates the bank successfully")
    public ResponseEntity<Bank> createBank(@Parameter(description = "Bank Object", required = true) @Valid @RequestBody Bank newBank) {
        Bank savedBank = bankService.createBank(newBank);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBank);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a bank by ID")
    @ApiResponse(responseCode = "200", description = "Updates the bank successfully")
    @ApiResponse(responseCode = "404", description = "Does not update a bank not found")
    public ResponseEntity<Bank> updateBank(
            @Parameter(description = "Bank ID", required = true) @PathVariable Long id,
            @Parameter(description = "Bank Object", required = true) @Valid @RequestBody Bank updatedBank
    ) {
        Optional<Bank> bankOptional = bankService.updateBank(id, updatedBank);
        return bankOptional.isPresent() ? ResponseEntity.ok(bankOptional.get()) : ResponseEntity.notFound().build();
    }
}
