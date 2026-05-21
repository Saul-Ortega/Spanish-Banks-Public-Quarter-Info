package org.example.spanishbanksquarterinfoapi.operation_section;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/operations/{operationId}/operation-sections")
public class OperationSectionController {
    private final OperationSectionService operationSectionService;

    public OperationSectionController(OperationSectionService operationSectionService) {
        this.operationSectionService = operationSectionService;
    }

    @GetMapping
    public ResponseEntity<List<OperationSection>> findAll(@PathVariable Long operationId) {
        List<OperationSection> operationSectionList = operationSectionService.findByOperationId(operationId);
        return ResponseEntity.ok(operationSectionList);
    }

    @GetMapping("id/{requestedId}")
    public ResponseEntity<OperationSection> findById(@PathVariable Long operationId, @PathVariable Long requestedId) {
        Optional<OperationSection> operationSectionOptional = operationSectionService.findByOperationIdAndId(operationId, requestedId);
        return operationSectionOptional.isPresent() ? ResponseEntity.ok(operationSectionOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OperationSection> createOperationSection(@PathVariable Long operationId, @RequestBody OperationSection newOperationSection) {
        OperationSection savedOperationSection = operationSectionService.createOperationSection(operationId, newOperationSection);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOperationSection);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<OperationSection> updateOperationSection(@PathVariable Long operationId, @PathVariable Long id, @RequestBody OperationSection updatedOperationSection) {
        Optional<OperationSection> operationSectionOptional = operationSectionService.updateOperationSection(operationId, id, updatedOperationSection);
        return operationSectionOptional.isPresent() ? ResponseEntity.ok(operationSectionOptional.get()) : ResponseEntity.notFound().build();
    }
}
