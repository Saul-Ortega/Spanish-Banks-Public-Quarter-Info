package org.example.spanishbanksquarterinfoapi.operation_section;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperationSectionRepository extends JpaRepository<OperationSection, Long> {
    Optional<OperationSection> findByOperationIdAndId(Long operationId, Long id);
    List<OperationSection> findByOperationId(Long operationId);
    Optional<OperationSection> findByType(String type);
}
