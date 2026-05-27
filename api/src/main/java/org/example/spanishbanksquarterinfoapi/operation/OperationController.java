package org.example.spanishbanksquarterinfoapi.operation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/declarations/{declarationId}/operations")
@Tag(name = "operation", description = "Create, read and update operations")
public class OperationController {
    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    @io.swagger.v3.oas.annotations.Operation(summary = "Gets all operations")
    @ApiResponse(responseCode = "200", description = "Returns all operations successfully")
    public ResponseEntity<List<Operation>> findByDeclarationId(@Parameter(description = "Declaration ID", required = true) @PathVariable Long declarationId) {
        List<Operation> operationList = operationService.findByDeclarationId(declarationId);
        return ResponseEntity.ok(operationList);
    }

    @GetMapping("/{requestedId}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Gets an operation by ID")
    @ApiResponse(responseCode = "200", description = "Returns the operation successfully")
    @ApiResponse(responseCode = "404", description = "Returns operation not found")
    public ResponseEntity<Operation> findByDeclarationIdAndId(
            @Parameter(description = "Declaration ID", required = true) @PathVariable Long declarationId,
            @Parameter(description = "Operation ID", required = true) @PathVariable Long requestedId
    ) {
        Optional<Operation> operationOptional = operationService.findByDeclarationIdAndId(declarationId, requestedId);
        return operationOptional.isPresent() ? ResponseEntity.ok(operationOptional.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping(params = "type")
    @io.swagger.v3.oas.annotations.Operation(summary = "Gets an operation by type")
    @ApiResponse(responseCode = "200", description = "Returns the operation successfully")
    @ApiResponse(responseCode = "404", description = "Returns the operation not found")
    public ResponseEntity<Operation> findByType(@Parameter(description = "Requested Type", required = true) @RequestParam String type) {
        Optional<Operation> operationOptional = operationService.findByType(type);
        return operationOptional.isPresent() ? ResponseEntity.ok(operationOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @io.swagger.v3.oas.annotations.Operation(summary = "Creates an operation")
    @ApiResponse(responseCode = "201", description = "Creates the operation successfully")
    public ResponseEntity<Operation> createOperation(
            @Parameter(description = "Declaration ID", required = true) @PathVariable Long declarationId,
            @Parameter(description = "Operation Object", required = true) @RequestBody Operation newOperation
    ) {
        Operation savedOperation = operationService.createOperation(declarationId, newOperation);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOperation);
    }

    @PutMapping("/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Updates an operation by ID")
    @ApiResponse(responseCode = "200", description = "Updates an operation successfully")
    @ApiResponse(responseCode = "404", description = "Does not update an operation not found")
    public ResponseEntity<Operation> updateOperation(
            @Parameter(description = "Declaration ID", required = true) @PathVariable Long declarationId,
            @Parameter(description = "Operation ID", required = true) @PathVariable Long id,
            @Parameter(description = "Operation Object", required = true) @RequestBody Operation updatedOperation
    ) {
        Optional<Operation> operationOptional = operationService.updateOperation(declarationId, id, updatedOperation);
        return operationOptional.isPresent() ? ResponseEntity.ok(operationOptional.get()) : ResponseEntity.notFound().build();
    }
}
