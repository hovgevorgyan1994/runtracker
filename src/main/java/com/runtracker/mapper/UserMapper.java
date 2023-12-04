package com.runtracker.mapper;

import com.runtracker.dto.user.CreateUserRequest;
import com.runtracker.dto.user.UserResponse;
import com.runtracker.entity.User;

public interface UserMapper {

  User mapToEntity(CreateUserRequest createUserRequest);

  UserResponse mapToDto(User user);
}
