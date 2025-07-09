package com.example.ExpenseTracker.services;

import com.example.ExpenseTracker.dto.CategorySummaryDTO;
import com.example.ExpenseTracker.dto.MonthlySummaryDTO;
import com.example.ExpenseTracker.dto.TransactionRequestDTO;
import com.example.ExpenseTracker.dto.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {
    TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO);
    List<TransactionResponseDTO> getTransactionsByUser(Long userId);
    void deleteTransaction(Long id);
    MonthlySummaryDTO getMonthlySummary(int month, int year);
    List<CategorySummaryDTO> getCategorySummary(int month, int year);
    List<TransactionResponseDTO> getAllTransactions();
}
