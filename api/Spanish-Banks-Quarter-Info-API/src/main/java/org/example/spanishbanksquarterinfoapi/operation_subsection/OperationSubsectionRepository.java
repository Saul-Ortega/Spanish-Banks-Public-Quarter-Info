package org.example.spanishbanksquarterinfoapi.operation_subsection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperationSubsectionRepository extends JpaRepository<OperationSubsection, Long> {
    Optional<OperationSubsection> findByOperationSectionIdAndId(Long operationSectionId, Long id);
    List<OperationSubsection> findByOperationSectionId(Long operationSectionId);
}
