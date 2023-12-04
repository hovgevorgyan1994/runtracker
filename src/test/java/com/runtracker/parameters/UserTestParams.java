package com.runtracker.parameters;

import static com.runtracker.dto.user.Sex.MALE;
import static java.time.Month.OCTOBER;
import static lombok.AccessLevel.PRIVATE;

import com.runtracker.dto.user.CreateUserRequest;
import com.runtracker.dto.user.EditUserRequest;
import com.runtracker.dto.user.UserResponse;
import com.runtracker.entity.User;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class UserTestParams {

  public static CreateUserRequest validCreateUserRequest() {
    return CreateUserRequest.builder()
        .firstName("John")
        .lastName("Lennon")
        .email("johnlennon@gmail.com")
        .birthDate(LocalDate.of(1940, OCTOBER, 9))
        .sex(MALE)
        .build();
  }

  public static CreateUserRequest invalidCreateUserRequest() {
    return CreateUserRequest.builder()
        .firstName("John")
        .email("johnlennon@gmail.com")
        .birthDate(LocalDate.of(1940, OCTOBER, 9))
        .sex(MALE)
        .build();
  }

  public static User user() {
    return User.builder()
        .id(1L)
        .firstName("John")
        .lastName("Lennon")
        .email("johnlennon@gmail.com")
        .birthDate(LocalDate.of(1940, OCTOBER, 9))
        .sex(MALE.name())
        .build();
  }

  public static UserResponse userResponse() {
    var birthDate = LocalDate.of(1940, OCTOBER, 9);
    return UserResponse.of(1L, "John", "Lennon", birthDate);
  }

  public static UserResponse editedUserResponse() {
    var birthDate = LocalDate.of(1940, OCTOBER, 9);
    return UserResponse.of(1L, "Jack", "London", birthDate);
  }

  public static EditUserRequest editUserRequest() {
    return EditUserRequest.builder()
        .firstName("Jack")
        .lastName("London")
        .birthDate(LocalDate.of(1940, OCTOBER, 9))
        .sex(MALE)
        .build();
  }
}
