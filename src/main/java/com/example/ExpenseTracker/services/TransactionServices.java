package com.example.ExpenseTracker.services;

import com.example.ExpenseTracker.model.Transaction;
import com.example.ExpenseTracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServices {
    private final TransactionRepository transactionRepository;

    public TransactionServices(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}
