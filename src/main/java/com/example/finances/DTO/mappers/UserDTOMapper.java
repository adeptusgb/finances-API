package com.example.finances.DTO.mappers;

import com.example.finances.DTO.UserDTO;
import com.example.finances.entities.user.User;
import com.example.finances.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    ExpenseService expenseService;
    @Autowired
    public UserDTOMapper(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Override
    public UserDTO apply(User user){
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                expenseService.getAllExpenses(user.getId())
        );
    }
}
