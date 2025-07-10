package com.example.ExpenseTracker.authdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String email;
}
