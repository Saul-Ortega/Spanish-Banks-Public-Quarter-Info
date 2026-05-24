package org.example.spanishbanksquarterinfoapi.operation_section;

import org.example.spanishbanksquarterinfoapi.operation.OperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationSectionService {
    private final OperationSectionRepository operationSectionRepository;
    private final OperationRepository operationRepository;

    public OperationSectionService(OperationSectionRepository operationSectionRepository, OperationRepository operationRepository) {
        this.operationSectionRepository = operationSectionRepository;
        this.operationRepository = operationRepository;
    }

    public Optional<OperationSection> findByOperationIdAndId(Long operationId, Long id) {
        return operationSectionRepository.findByOperationIdAndId(operationId, id);
    }

    public List<OperationSection> findByOperationId(Long operationId) {
        return operationSectionRepository.findByOperationId(operationId);
    }

    public OperationSection createOperationSection(Long operationId, OperationSection operationSection) {
        operationSection.setOperation(operationRepository.getReferenceById(operationId));
        return operationSectionRepository.save(operationSection);
    }

    public Optional<OperationSection> updateOperationSection(Long operationId, Long id, OperationSection updatedOperationSection) {
        return operationSectionRepository.findById(id)
                .map(operationSection -> {
                    operationSection.setType(updatedOperationSection.getType());
                    operationSection.setOperation(operationRepository.getReferenceById(operationId));
                    return operationSectionRepository.save(operationSection);
                });
    }
}
