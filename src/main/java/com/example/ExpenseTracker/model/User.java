package com.example.ExpenseTracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String email;
}
