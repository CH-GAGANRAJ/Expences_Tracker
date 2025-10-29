package com.mariatemp.expenses.mapper;

import com.mariatemp.expenses.dto.UserDto;
import com.mariatemp.expenses.model.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail()
        );
    }

    public static User toEntity(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        return user;
    }
}
