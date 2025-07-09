package com.example.ExpenseTracker.controller;

import com.example.ExpenseTracker.model.Transaction;
import com.example.ExpenseTracker.services.TransactionServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionServices transactionServices;

    public TransactionController(TransactionServices transactionServices) {
        this.transactionServices = transactionServices;
    }

    public List<Transaction> getUserTransactions(@PathVariable Long userId) {
        return transactionServices.getTransactionByUser(userId);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionServices.save(transaction);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        transactionServices.delete(id);
    }
}
