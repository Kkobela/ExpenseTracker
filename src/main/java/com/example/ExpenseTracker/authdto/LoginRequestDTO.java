package com.example.ExpenseTracker.authdto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}
