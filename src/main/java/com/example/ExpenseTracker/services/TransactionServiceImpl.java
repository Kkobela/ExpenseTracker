package com.example.ExpenseTracker.services;

import com.example.ExpenseTracker.transactiondto.CategorySummaryDTO;
import com.example.ExpenseTracker.transactiondto.MonthlySummaryDTO;
import com.example.ExpenseTracker.transactiondto.TransactionRequestDTO;
import com.example.ExpenseTracker.transactiondto.TransactionResponseDTO;
import com.example.ExpenseTracker.mapper.TransactionMapper;
import com.example.ExpenseTracker.model.Category;
import com.example.ExpenseTracker.model.Transaction;
import com.example.ExpenseTracker.model.TransactionType;
import com.example.ExpenseTracker.model.User;
import com.example.ExpenseTracker.repository.CategoryRepository;
import com.example.ExpenseTracker.repository.TransactionRepository;
import com.example.ExpenseTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO) {
        Transaction transaction = new Transaction();
        transaction.setDescription(requestDTO.getDescription());
        transaction.setAmount(requestDTO.getAmount());
        transaction.setType(requestDTO.getType());
        transaction.setDate(requestDTO.getDate());

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        transaction.setUser(user);

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        transaction.setCategory(category);

        Transaction saved = transactionRepository.save(transaction);
        return toDTO(saved);
    }

    @Override
    public List<TransactionResponseDTO> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    private TransactionResponseDTO toDTO(Transaction entity) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setAmount(entity.getAmount());
        dto.setType(entity.getType());
        dto.setDate(entity.getDate());

        if (entity.getCategory() != null) {
            dto.setCategoryId(entity.getCategory().getId());
        }

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        return dto;
    }

    public MonthlySummaryDTO getMonthlySummary(int month, int year) {
        List<Object[]> results = transactionRepository.getMonthlyTotals(month, year);

        BigDecimal totalExpense = BigDecimal.ZERO;
        BigDecimal totalIncome = BigDecimal.ZERO;

        for (Object[] row : results) {
            TransactionType type = (TransactionType) row[0];
            BigDecimal sum = (BigDecimal) row[1];

            if (type == TransactionType.EXPENSE) {
                totalExpense = sum;
            } else if (type == TransactionType.INCOME) {
                totalIncome = sum;
            }
        }

        return new MonthlySummaryDTO(year, month, totalExpense, totalIncome);
    }

    public List<CategorySummaryDTO> getCategorySummary(int month, int year) {
        List<Object[]> results = transactionRepository.getCategoryTotals(month, year);
        List<CategorySummaryDTO> summaries = new ArrayList<>();

        for (Object[] row : results) {
            Long categoryId = (Long) row[0];
            String categoryName = (String) row[1];
            BigDecimal total = (BigDecimal) row[2];

            summaries.add(new CategorySummaryDTO(categoryId, categoryName, total));
        }

        return summaries;
    }

    @Override
    public List<TransactionResponseDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }
}
