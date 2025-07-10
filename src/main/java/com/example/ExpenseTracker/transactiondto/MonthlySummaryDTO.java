package com.example.ExpenseTracker.transactiondto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySummaryDTO {
    private int year;
    private int month;
    private BigDecimal totalExpense = BigDecimal.ZERO;
    private BigDecimal totalIncome = BigDecimal.ZERO;
}
