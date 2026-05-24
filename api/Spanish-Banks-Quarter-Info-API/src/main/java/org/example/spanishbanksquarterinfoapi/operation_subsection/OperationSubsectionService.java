package org.example.spanishbanksquarterinfoapi.operation_subsection;

import org.example.spanishbanksquarterinfoapi.operation_section.OperationSectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationSubsectionService {
    private final OperationSubsectionRepository operationSubsectionRepository;
    private final OperationSectionRepository operationSectionRepository;

    public OperationSubsectionService(OperationSubsectionRepository operationSubsectionRepository, OperationSectionRepository operationSectionRepository) {
        this.operationSubsectionRepository = operationSubsectionRepository;
        this.operationSectionRepository = operationSectionRepository;
    }

    public Optional<OperationSubsection> findByOperationSectionIdAndId(Long operationSectionId, Long id) {
        return operationSubsectionRepository.findByOperationSectionIdAndId(operationSectionId, id);
    }

    public List<OperationSubsection> findByOperationSectionId(Long operationSectionId) {
        return operationSubsectionRepository.findByOperationSectionId(operationSectionId);
    }

    public OperationSubsection createOperationSubsection(Long operationSectionId, OperationSubsection subsection) {
        subsection.setOperationSection(operationSectionRepository.getReferenceById(operationSectionId));
        return operationSubsectionRepository.save(subsection);
    }

    public Optional<OperationSubsection> updateOperationSubsection(Long operationSectionId, Long id, OperationSubsection updatedSubsection) {
        return operationSubsectionRepository.findById(id)
                .map(operationSubsection -> {
                    operationSubsection.setType(updatedSubsection.getType());
                    operationSubsection.setPracticed(updatedSubsection.isPracticed());
                    operationSubsection.setData(updatedSubsection.getData());
                    operationSubsection.setOperationSection(operationSectionRepository.getReferenceById(operationSectionId));
                    return operationSubsectionRepository.save(operationSubsection);
                });
    }
}
