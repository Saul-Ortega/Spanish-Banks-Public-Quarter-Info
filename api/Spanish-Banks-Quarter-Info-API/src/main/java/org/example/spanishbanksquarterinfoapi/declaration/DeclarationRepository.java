package org.example.spanishbanksquarterinfoapi.declaration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeclarationRepository extends JpaRepository<Declaration, Long> {
    Optional<Declaration> findByBankIdAndId(Long bankId, Long id);
    List<Declaration> findByBankId(Long bankId);
    Optional<Declaration> findByQuarter(String quarter);
}
