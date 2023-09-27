package com.example.finances.controllers;

import com.example.finances.DTO.ExpenseDTO;
import com.example.finances.entities.Expense;
import com.example.finances.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/users/{userId}/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses(@PathVariable("userId") Long userId){
        return new ResponseEntity<>(expenseService.getAllExpenses(userId), HttpStatus.OK);
    }

    @GetMapping(path = "/{expenseId}")
    public ResponseEntity<ExpenseDTO> getExpense(@PathVariable("userId") Long userId, @PathVariable("expenseId") Long expenseId){
        return new ResponseEntity<>(expenseService.getExpense(userId, expenseId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody Expense expense, @PathVariable("userId") Long userId){
        return new ResponseEntity<>(expenseService.createExpense(expense, userId), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{expenseId}")
    public ResponseEntity<?> updateExpense(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal cost,
            @PathVariable("userId") Long userId,
            @PathVariable("expenseId") Long expenseId){
        expenseService.updateExpense(name, cost, userId, expenseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{expenseId}")
    public ResponseEntity<?> deleteExpense(@PathVariable("userId") Long userId, @PathVariable("expenseId") Long expenseId){
        expenseService.deleteExpense(userId, expenseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}