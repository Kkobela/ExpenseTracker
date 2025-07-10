package com.example.ExpenseTracker.transactiondto;

import com.example.ExpenseTracker.model.Transaction;
import com.example.ExpenseTracker.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionResponseDTO {
    private Long id;
    private String description;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDate date;
    private Long categoryId;
    private Long userId;

    public TransactionResponseDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.description = transaction.getDescription();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.date = transaction.getDate();
        this.categoryId = transaction.getCategory() != null ? transaction.getCategory().getId() : null;
        this.userId = transaction.getUser() != null ? transaction.getUser().getId() : null;
    }
}
