package com.runtracker.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.runtracker.dto.user.CreateUserRequest;
import com.runtracker.dto.user.EditUserRequest;
import com.runtracker.dto.user.UserResponse;
import com.runtracker.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserResponse> register(
      @RequestBody @Valid CreateUserRequest createUserRequest) {
    return ResponseEntity.status(CREATED).body(userService.register(createUserRequest));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> edit(@PathVariable Long id,
      @RequestBody @Valid EditUserRequest editUserRequest) {
    return ResponseEntity.ok(userService.edit(id, editUserRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getById(id));
  }

  @GetMapping
  public ResponseEntity<List<UserResponse>> getAll() {
    return ResponseEntity.ok(userService.getAll());
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> remove(@PathVariable Long id) {
    userService.remove(id);
    return ResponseEntity.ok().build();
  }
}
