package com.example.ExpenseTracker.controller;

import com.example.ExpenseTracker.dto.CategorySummaryDTO;
import com.example.ExpenseTracker.dto.MonthlySummaryDTO;
import com.example.ExpenseTracker.dto.TransactionRequestDTO;
import com.example.ExpenseTracker.dto.TransactionResponseDTO;
import com.example.ExpenseTracker.services.TransactionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/summary/categories")
    public List<CategorySummaryDTO> getCategorySummary(
            @RequestParam int year,
            @RequestParam int month) {
        return transactionServices.getCategorySummary(month, year);
    }

    @GetMapping(value = "/export/csv", produces = "text/csv")
    public ResponseEntity<String> exportTransactionsToCsv() {
        List<TransactionResponseDTO> transactions = transactionServices.getAllTransactions();

        StringBuilder csv = new StringBuilder();
        csv.append("id,description,amount,type,date,categoryId,userId\n");

        for (TransactionResponseDTO dto : transactions) {
            csv.append(dto.getId()).append(",")
                    .append(dto.getDescription()).append(",")
                    .append(dto.getAmount()).append(",")
                    .append(dto.getType()).append(",")
                    .append(dto.getDate()).append(",")
                    .append(dto.getCategoryId()).append(",")
                    .append(dto.getUserId()).append("\n");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.csv")
                .body(csv.toString());
    }

}
