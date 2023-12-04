package com.runtracker.service.impl;

import static com.runtracker.exception.Error.USER_NOT_FOUND;

import com.runtracker.entity.User;
import com.runtracker.exception.NotFoundException;
import com.runtracker.repository.UserRepository;
import com.runtracker.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

  private final UserRepository userRepository;

  @Override
  public User getUser(Long id) {
    return userRepository.findById(id).orElseThrow(() -> {
      log.info("There is no user with ID: {}", id);
      return new NotFoundException(USER_NOT_FOUND);
    });
  }
}
