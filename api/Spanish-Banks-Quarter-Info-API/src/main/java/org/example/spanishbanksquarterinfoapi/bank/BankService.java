package org.example.spanishbanksquarterinfoapi.bank;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {
    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Optional<Bank> findById(Long id) {
        return bankRepository.findById(id);
    }

    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public Optional<Bank> updateBank(Long id, Bank updatedBank) {
        return bankRepository.findById(id)
                .map(bank -> {
                   bank.setEntity(updatedBank.getEntity());
                   bank.setDenomination(updatedBank.getDenomination());
                   return bankRepository.save(bank);
                });
    }
}
