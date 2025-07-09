package com.example.ExpenseTracker.mapper;

import com.example.ExpenseTracker.dto.TransactionRequestDTO;
import com.example.ExpenseTracker.dto.TransactionResponseDTO;
import com.example.ExpenseTracker.model.Transaction;
import com.example.ExpenseTracker.repository.CategoryRepository;
import com.example.ExpenseTracker.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TransactionMapper(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Transaction toEntity(TransactionRequestDTO dto) {
        Transaction t = new Transaction();
        t.setDescription(dto.getDescription());
        t.setAmount(dto.getAmount());
        t.setType(dto.getType());
        t.setDate(dto.getDate());

        t.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId())));

        t.setUser(userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUserId())));

        return t;
    }

    public TransactionResponseDTO toDTO(Transaction entity) {
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
}
