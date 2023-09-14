package com.example.finances.DTO.mappers;

import com.example.finances.DTO.ExpenseDTO;
import com.example.finances.entities.Expense;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ExpenseDTOMapper implements Function<Expense, ExpenseDTO> {
    @Override
    public ExpenseDTO apply(Expense expense){
        return new ExpenseDTO(
                expense.getExpenseId(),
                expense.getName(),
                expense.getCost(),
                expense.getDate()
        );
    }
}
