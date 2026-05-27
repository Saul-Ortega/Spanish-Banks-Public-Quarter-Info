package org.example.spanishbanksquarterinfoapi.operation_section;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operations/{operationId}/operation-sections")
@Tag(name = "operation section", description = "Create, read and update operation sections")
public class OperationSectionController {
    private final OperationSectionService operationSectionService;

    public OperationSectionController(OperationSectionService operationSectionService) {
        this.operationSectionService = operationSectionService;
    }

    @GetMapping
    @Operation(summary = "Gets all operation sections")
    @ApiResponse(responseCode = "200", description = "Returns all operation sections")
    public ResponseEntity<List<OperationSection>> findAll(@Parameter(description = "Operation ID", required = true) @PathVariable Long operationId) {
        List<OperationSection> operationSectionList = operationSectionService.findByOperationId(operationId);
        return ResponseEntity.ok(operationSectionList);
    }

    @GetMapping("/{requestedId}")
    @Operation(summary = "Gets an operation section by ID")
    @ApiResponse(responseCode = "200", description = "Returns the operation section successfully")
    @ApiResponse(responseCode = "404", description = "Returns operation section not found")
    public ResponseEntity<OperationSection> findById(
            @Parameter(description = "Operation ID", required = true) @PathVariable Long operationId,
            @Parameter(description = "Operation Section ID", required = true) @PathVariable Long requestedId
    ) {
        Optional<OperationSection> operationSectionOptional = operationSectionService.findByOperationIdAndId(operationId, requestedId);
        return operationSectionOptional.isPresent() ? ResponseEntity.ok(operationSectionOptional.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping(params = "type")
    @Operation(summary = "Gets an operation section by type")
    @ApiResponse(responseCode = "200", description = "Returns the operation section successfully")
    @ApiResponse(responseCode = "404", description = "Returns operation section not found")
    public ResponseEntity<OperationSection> findByType(@Parameter(description = "Requested Type", required = true) @RequestParam String type) {
        Optional<OperationSection> operationSectionOptional = operationSectionService.findByType(type);
        return operationSectionOptional.isPresent() ? ResponseEntity.ok(operationSectionOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Creates an operation section")
    @ApiResponse(responseCode = "201", description = "Creates an operation section successfully")
    public ResponseEntity<OperationSection> createOperationSection(
            @Parameter(description = "Operation ID", required = true) @PathVariable Long operationId,
            @Parameter(description = "Operation Section Object", required = true) @RequestBody OperationSection newOperationSection
    ) {
        OperationSection savedOperationSection = operationSectionService.createOperationSection(operationId, newOperationSection);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOperationSection);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an operation section by ID")
    @ApiResponse(responseCode = "200", description = "Updates an operation section successfully")
    @ApiResponse(responseCode = "404", description = "Does not update an operation section not found")
    public ResponseEntity<OperationSection> updateOperationSection(
            @Parameter(description = "Operation ID", required = true) @PathVariable Long operationId,
            @Parameter(description = "Operation Section ID", required = true) @PathVariable Long id,
            @Parameter(description = "Operation Section Object") @RequestBody OperationSection updatedOperationSection
    ) {
        Optional<OperationSection> operationSectionOptional = operationSectionService.updateOperationSection(operationId, id, updatedOperationSection);
        return operationSectionOptional.isPresent() ? ResponseEntity.ok(operationSectionOptional.get()) : ResponseEntity.notFound().build();
    }
}
