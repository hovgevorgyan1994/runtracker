package com.runtracker.mapper.impl;

import com.runtracker.dto.user.CreateUserRequest;
import com.runtracker.dto.user.UserResponse;
import com.runtracker.entity.User;
import com.runtracker.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

  @Override
  public User mapToEntity(CreateUserRequest request) {
    return new User(
        request.getFirstName(),
        request.getLastName(),
        request.getEmail(),
        request.getBirthDate(),
        request.getSex().name());
  }

  @Override
  public UserResponse mapToDto(User user) {
    return UserResponse.of(user.getId(), user.getFirstName(), user.getLastName(),
        user.getBirthDate());
  }
}
