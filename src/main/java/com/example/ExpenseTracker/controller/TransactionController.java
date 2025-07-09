package com.example.ExpenseTracker.controller;

import com.example.ExpenseTracker.dto.MonthlySummaryDTO;
import com.example.ExpenseTracker.dto.TransactionRequestDTO;
import com.example.ExpenseTracker.dto.TransactionResponseDTO;
import com.example.ExpenseTracker.services.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionServices;

    public TransactionController(TransactionService transactionServices) {
        this.transactionServices = transactionServices;
    }

    @GetMapping("/user/{userId}")
    public List<TransactionResponseDTO> getUserTransactions(@PathVariable Long userId) {
        return transactionServices.getTransactionsByUser(userId);
    }

    @PostMapping
    public TransactionResponseDTO createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        return transactionServices.createTransaction(transactionRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        transactionServices.deleteTransaction(id);
    }

    @GetMapping("/summary")
    public MonthlySummaryDTO getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month) {
        return transactionServices.getMonthlySummary(month, year);
    }

}
