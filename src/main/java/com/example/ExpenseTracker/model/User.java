package com.example.ExpenseTracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "app_user")
@Getter
public class User {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String email;
}
