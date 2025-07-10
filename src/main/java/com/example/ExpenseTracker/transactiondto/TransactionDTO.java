package com.example.ExpenseTracker.transactiondto;

import com.example.ExpenseTracker.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionDTO {
    private String description;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDate date;
    private Long categoryId;
    private Long userId;
}
