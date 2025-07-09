package com.example.ExpenseTracker.repository;

import com.example.ExpenseTracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);

    @Query("SELECT t.type, SUM(t.amount) " +
            "FROM Transaction t " +
            "WHERE MONTH(t.date) = :month AND YEAR(t.date) = :year " +
            "GROUP BY t.type")
    List<Object[]> getMonthlyTotals(@Param("month") int month, @Param("year") int year);

    @Query("SELECT t.category.id, t.category.name, SUM(t.amount) " +
            "FROM Transaction t " +
            "WHERE MONTH(t.date) = :month AND YEAR(t.date) = :year " +
            "GROUP BY t.category.id, t.category.name")
    List<Object[]> getCategoryTotals(@Param("month") int month, @Param("year") int year);
}
