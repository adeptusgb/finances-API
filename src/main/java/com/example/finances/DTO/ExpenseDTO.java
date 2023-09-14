package com.example.finances.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseDTO(
        Long id,
        String name,
        BigDecimal cost,
        LocalDate date
) {}
