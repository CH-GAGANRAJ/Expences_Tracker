package com.mariatemp.expenses.service;

import com.mariatemp.expenses.dto.ExpenseDto;
import com.mariatemp.expenses.dto.UserDto;
import com.mariatemp.expenses.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User saveUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    void deleteUser(Long id);

    Optional<User> getUserByUsername(String username);
    List<UserDto> getAllUsersDto();
    Optional<UserDto> getUserDtoById(Long id);
    Optional<List<ExpenseDto>> getUserExpensesDto(Long id);
    UserDto createUser(UserDto userDto);
    Optional<UserDto> updateUser(Long id, UserDto userDto);
}
