package com.example.ExpenseTracker.controller;

import com.example.ExpenseTracker.dto.TransactionDTO;
import com.example.ExpenseTracker.dto.TransactionResponseDTO;
import com.example.ExpenseTracker.model.Category;
import com.example.ExpenseTracker.model.Transaction;
import com.example.ExpenseTracker.model.User;
import com.example.ExpenseTracker.repository.CategoryRepository;
import com.example.ExpenseTracker.repository.UserRepository;
import com.example.ExpenseTracker.services.TransactionServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionServices transactionServices;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionController(TransactionServices transactionServices, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.transactionServices = transactionServices;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/{userId}")
    public List<Transaction> getUserTransactions(@PathVariable Long userId) {
        return transactionServices.getTransactionByUser(userId);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = new Transaction();
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setDate(dto.getDate());
        transaction.setUser(user);
        transaction.setCategory(category);

        Transaction saved = transactionServices.save(transaction);

        TransactionResponseDTO response = new TransactionResponseDTO(saved);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        transactionServices.delete(id);
    }
}
