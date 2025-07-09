package com.example.ExpenseTracker.dto;

import com.example.ExpenseTracker.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TransactionRequestDTO {
    private String description;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDate date;
    private Long categoryId;
    private Long userId;
}
