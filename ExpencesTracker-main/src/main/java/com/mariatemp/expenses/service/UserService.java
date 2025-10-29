package com.mariatemp.expenses.service;

import com.mariatemp.expenses.dto.ExpenseDto;
import com.mariatemp.expenses.dto.UserDto;
import com.mariatemp.expenses.mapper.ExpenseMapper;
import com.mariatemp.expenses.mapper.UserMapper;
import com.mariatemp.expenses.model.User;
import com.mariatemp.expenses.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public List<UserDto> getAllUsersDto() {
        return getAllUsers().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getUserDtoById(Long id) {
        return getUserById(id).map(UserMapper::toDto);
    }

    @Override
    public Optional<List<ExpenseDto>> getUserExpensesDto(Long id) {
        return getUserById(id).map(user ->
                user.getExpenses().stream()
                        .map(ExpenseMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        User savedUser = saveUser(user);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public Optional<UserDto> updateUser(Long id, UserDto userDto) {
        return getUserById(id).map(existingUser -> {
            existingUser.setUsername(userDto.getUsername());
            existingUser.setEmail(userDto.getEmail());
            User updatedUser = saveUser(existingUser);
            return UserMapper.toDto(updatedUser);
        });
    }
}
