package com.example.finances.DTO;

import java.util.List;

public record UserDTO(
        Long id,
        String username,
        List<ExpenseDTO> expenses
) {}
