package com.runtracker.service;

import com.runtracker.dto.user.CreateUserRequest;
import com.runtracker.dto.user.EditUserRequest;
import com.runtracker.dto.user.UserResponse;
import java.util.List;

public interface UserService {

  UserResponse register(CreateUserRequest createUserRequest);

  UserResponse edit(Long id, EditUserRequest editUserRequest);

  UserResponse getById(Long id);

  List<UserResponse> getAll();

  void remove(Long id);
}
