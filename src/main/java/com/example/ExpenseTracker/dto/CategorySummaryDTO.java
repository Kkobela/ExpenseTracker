package com.example.ExpenseTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummaryDTO {
    private Long categoryId;
    private String categoryName;
    private BigDecimal total;
}
