package org.example.spanishbanksquarterinfoapi.operation;

import org.example.spanishbanksquarterinfoapi.declaration.DeclarationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final DeclarationRepository declarationRepository;

    public OperationService(OperationRepository operationRepository, DeclarationRepository declarationRepository) {
        this.operationRepository = operationRepository;
        this.declarationRepository = declarationRepository;
    }

    public Optional<Operation> findByDeclarationIdAndId(Long declarationId, Long id) {
        return operationRepository.findByDeclarationIdAndId(declarationId, id);
    }

    public List<Operation> findByDeclarationId(Long declarationId) {
        return operationRepository.findByDeclarationId(declarationId);
    }

    public Operation createOperation(Long declarationId, Operation operation) {
        operation.setDeclaration(declarationRepository.getReferenceById(declarationId));
        return operationRepository.save(operation);
    }

    public Optional<Operation> updateOperation(Long declarationId, Long id, Operation updatedOperation) {
        return operationRepository.findById(id)
                .map(operation -> {
                    operation.setType(updatedOperation.getType());
                    operation.setDeclaration(declarationRepository.getReferenceById(declarationId));
                    return operationRepository.save(operation);
                });
    }
}
