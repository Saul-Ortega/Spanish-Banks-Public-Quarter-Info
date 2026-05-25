package org.example.spanishbanksquarterinfoapi.operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/declarations/{declarationId}/operations")
public class OperationController {
    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public ResponseEntity<List<Operation>> findByDeclarationId(@PathVariable Long declarationId) {
        List<Operation> operationList = operationService.findByDeclarationId(declarationId);
        return ResponseEntity.ok(operationList);
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Operation> findByDeclarationIdAndId(@PathVariable Long declarationId, @PathVariable Long requestedId) {
        Optional<Operation> operationOptional = operationService.findByDeclarationIdAndId(declarationId, requestedId);
        return operationOptional.isPresent() ? ResponseEntity.ok(operationOptional.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping(params = "type")
    public ResponseEntity<Operation> findByType(@RequestParam String type) {
        Optional<Operation> operationOptional = operationService.findByType(type);
        return operationOptional.isPresent() ? ResponseEntity.ok(operationOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Operation> createOperation(@PathVariable Long declarationId, @RequestBody Operation newOperation) {
        Operation savedOperation = operationService.createOperation(declarationId, newOperation);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOperation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Operation> updateOperation(@PathVariable Long declarationId, @PathVariable Long id, @RequestBody Operation updatedOperation) {
        Optional<Operation> operationOptional = operationService.updateOperation(declarationId, id, updatedOperation);
        return operationOptional.isPresent() ? ResponseEntity.ok(operationOptional.get()) : ResponseEntity.notFound().build();
    }
}
