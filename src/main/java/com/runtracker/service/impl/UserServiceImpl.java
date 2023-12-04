package com.runtracker.service.impl;

import static com.runtracker.exception.Error.USER_ALREADY_EXISTS;

import com.runtracker.dto.user.CreateUserRequest;
import com.runtracker.dto.user.EditUserRequest;
import com.runtracker.dto.user.UserResponse;
import com.runtracker.exception.UserAlreadyExistException;
import com.runtracker.mapper.UserMapper;
import com.runtracker.repository.UserRepository;
import com.runtracker.service.CommonService;
import com.runtracker.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  private final UserRepository userRepository;
  private final CommonService commonService;

  @Override
  public UserResponse register(CreateUserRequest createUserRequest) {
    log.info("Attempt to register a new user. {}", createUserRequest);
    validateEmail(createUserRequest);
    var user = userRepository.save(userMapper.mapToEntity(createUserRequest));
    log.info("User {} successfully registered", user.getEmail());
    if (log.isDebugEnabled()) {
      log.info("User successfully registered. {}", user);
    }
    return userMapper.mapToDto(user);
  }

  @Override
  public UserResponse edit(Long id, EditUserRequest editUserRequest) {
    log.info("Attempt to update user details. ID: {}", id);
    if (log.isDebugEnabled()) {
      log.info("Attempt to edit user details. ID: {}, Details: {}", id, editUserRequest);
    }
    var user = commonService.getUser(id);

    //set operations will change the persistent entity after the commit
    //because the method gets executed in a Spring Transaction
    user.setFirstName(editUserRequest.getFirstName());
    user.setLastName(editUserRequest.getLastName());
    user.setBirthDate(editUserRequest.getBirthDate());
    user.setSex(editUserRequest.getSex().name());
    log.info("Successfully updated user details. ID: {}", id);
    return userMapper.mapToDto(user);
  }

  @Override
  public UserResponse getById(Long id) {
    log.info("Attempt to get user by ID: {}", id);
    var user = commonService.getUser(id);
    log.info("Found user with ID: {}", id);
    return userMapper.mapToDto(user);
  }

  @Override
  public List<UserResponse> getAll() {
    log.info("Attempt to get all users.");
    var users = userRepository.findAll().stream()
        .map(userMapper::mapToDto)
        .toList();
    log.info("Found {} users for getAll request.", users.size());
    return users;
  }

  @Override
  public void remove(Long id) {
    log.info("Attempt to remove user by ID: {}", id);
    var user = commonService.getUser(id);
    userRepository.delete(user);
    log.info("Successfully removed user with ID: {}", id);
  }

  private void validateEmail(CreateUserRequest createUserRequest) {
    var email = createUserRequest.getEmail();
    if (userRepository.existsByEmail(email)) {
      log.info("There is an existing user with email: {}", email);
      throw new UserAlreadyExistException(USER_ALREADY_EXISTS);
    }
  }
}
