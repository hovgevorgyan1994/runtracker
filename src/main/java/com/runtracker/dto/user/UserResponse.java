package com.runtracker.dto.user;

import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value(staticConstructor = "of")
@EqualsAndHashCode
public class UserResponse {

  Long id;
  String firstName;
  String lastName;
  LocalDate birthDate;
}
