package org.example.spanishbanksquarterinfoapi.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    Optional<Operation> findByDeclarationIdAndId(Long declarationId, Long id);
    List<Operation> findByDeclarationId(Long declarationId);
}
