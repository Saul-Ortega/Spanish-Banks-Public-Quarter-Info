package org.example.spanishbanksquarterinfoapi.declaration;

import org.example.spanishbanksquarterinfoapi.bank.Bank;
import org.example.spanishbanksquarterinfoapi.bank.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeclarationService {
    private final DeclarationRepository declarationRepository;
    private final BankRepository bankRepository;

    public DeclarationService(DeclarationRepository declarationRepository, BankRepository bankRepository) {
        this.declarationRepository = declarationRepository;
        this.bankRepository = bankRepository;
    }

    public Optional<Declaration> findByBankIdAndId(Long bankId, Long id) {
        return declarationRepository.findByBankIdAndId(bankId, id);
    }

    public List<Declaration> findByBankId(Long bankId) {
        return declarationRepository.findByBankId(bankId);
    }

    public Declaration createDeclaration(Long bankId, Declaration declaration) {
        declaration.setBank(bankRepository.getReferenceById(bankId));
        return declarationRepository.save(declaration);
    }

    public Optional<Declaration> updateDeclaration(Long bankId, Long id, Declaration updatedDeclaration) {
        return declarationRepository.findById(id)
                .map(declaration -> {
                   declaration.setQuarter(updatedDeclaration.getQuarter());
                   declaration.setType(updatedDeclaration.getType());
                   declaration.setDeclarationDate(updatedDeclaration.getDeclarationDate());
                   declaration.setPublishedDate(updatedDeclaration.getPublishedDate());
                   declaration.setBank(bankRepository.getReferenceById(bankId));
                   return declarationRepository.save(declaration);
                });
    }
}
