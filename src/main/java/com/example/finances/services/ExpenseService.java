package com.example.finances.services;

import com.example.finances.DTO.ExpenseDTO;
import com.example.finances.DTO.mappers.ExpenseDTOMapper;
import com.example.finances.entities.Expense;
import com.example.finances.entities.user.User;
import com.example.finances.exceptions.ResourceAccessDeniedException;
import com.example.finances.exceptions.ResourceNotFoundException;
import com.example.finances.repositories.ExpenseRepository;
import com.example.finances.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseDTOMapper expenseDTOMapper;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Autowired
    public ExpenseService(ExpenseDTOMapper expenseDTOMapper, ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseDTOMapper = expenseDTOMapper;
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public List<ExpenseDTO> getAllExpenses(Long userId){
        return expenseRepository.findByUser_Id(userId)
                .stream()
                .map(expenseDTOMapper)
                .collect(Collectors.toList());
    }
    public ExpenseDTO getExpense(Long userId, Long expenseId){
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "expense with id [%s] not found".formatted(expenseId)
                ));

        if(!expense.getUser().getId().equals(userId)){
            throw new ResourceAccessDeniedException("the expense id provided is from someone else");
        }

        return expenseDTOMapper.apply(expense);
    }
    public ExpenseDTO createExpense(Expense expense, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "user with id [%s] not found".formatted(userId)
                ));

        if(expense.getCost().signum() > 0 && !expense.getName().trim().isEmpty()) {
            Expense createdExpense =
                    new Expense(
                            expense.getName(),
                            expense.getCost(),
                            user
                            );
            expenseRepository.save(createdExpense);
            return expenseDTOMapper.apply(createdExpense);
        }
        throw new IllegalStateException("name or cost invalid");
    }
    public void updateExpense(String name, BigDecimal cost, Long userId, Long expenseId){
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "expense with id [%s] not found".formatted(expenseId)
                ));

        if(!expense.getUser().getId().equals(userId)){
            throw new ResourceAccessDeniedException("expense with id [%s] belongs to someone else".formatted(expenseId));
        }

        if(name != null) {
            expense.setName(name);
        }
        if(cost != null) {
            expense.setCost(cost);
        }
        expenseRepository.save(expense);
    }
    public void deleteExpense(Long userId, Long expenseId){
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "expense with id [%s] not found".formatted(expenseId)
                ));

        if(!expense.getUser().getId().equals(userId)){
            throw new ResourceAccessDeniedException("expense with id [%s] belongs to someone else".formatted(expenseId));
        }

        expenseRepository.deleteById(expenseId);
    }
}
