package com.example.ExpenseTracker.userdto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDTO {
    private String username;
    private String password;
    private String email;
}
