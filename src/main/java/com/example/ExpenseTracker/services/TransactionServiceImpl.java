package com.example.ExpenseTracker.services;

import com.example.ExpenseTracker.dto.TransactionRequestDTO;
import com.example.ExpenseTracker.dto.TransactionResponseDTO;
import com.example.ExpenseTracker.model.Category;
import com.example.ExpenseTracker.model.Transaction;
import com.example.ExpenseTracker.model.User;
import com.example.ExpenseTracker.repository.CategoryRepository;
import com.example.ExpenseTracker.repository.TransactionRepository;
import com.example.ExpenseTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

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
}
