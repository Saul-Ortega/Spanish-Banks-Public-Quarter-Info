package org.example.spanishbanksquarterinfoapi.operation_subsection;

import org.example.spanishbanksquarterinfoapi.operation_section.OperationSection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/operation-sections/{operationSectionId}/operation-subsections")
public class OperationSubsectionController {
    private final OperationSubsectionService operationSubsectionService;

    public OperationSubsectionController(OperationSubsectionService operationSubsectionService) {
        this.operationSubsectionService = operationSubsectionService;
    }

    @GetMapping
    public ResponseEntity<List<OperationSubsection>> findByOperationSectionId(@PathVariable Long operationSectionId) {
        List<OperationSubsection> operationSubsectionList = operationSubsectionService.findByOperationSectionId(operationSectionId);
        return ResponseEntity.ok(operationSubsectionList);
    }

    @GetMapping("id/{requestedId}")
    public ResponseEntity<OperationSubsection> findByOperationSectionIdAndId(@PathVariable Long operationSectionId, @PathVariable Long requestedId) {
        Optional<OperationSubsection> operationSubsectionOptional = operationSubsectionService.findByOperationSectionIdAndId(operationSectionId, requestedId);
        return operationSubsectionOptional.isPresent() ? ResponseEntity.ok(operationSubsectionOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OperationSubsection> createOperationSubsection(@PathVariable Long operationSectionId, @RequestBody OperationSubsection newOperationSubsection) {
        OperationSubsection savedOperationSubsection = operationSubsectionService.createOperationSubsection(operationSectionId, newOperationSubsection);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOperationSubsection);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<OperationSubsection> updateOperationSubsection(@PathVariable Long operationSectionId, @PathVariable Long id, @RequestBody OperationSubsection updatedOperationSubsection) {
        Optional<OperationSubsection> operationSubsectionOptional = operationSubsectionService.updateOperationSubsection(operationSectionId, id, updatedOperationSubsection);
        return operationSubsectionOptional.isPresent() ? ResponseEntity.ok(operationSubsectionOptional.get()) : ResponseEntity.notFound().build();
    }
}
