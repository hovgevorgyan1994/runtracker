package com.runtracker.dto.user;

import com.runtracker.validation.Name;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

  @Name
  @NotBlank(message = "Can't be empty!")
  private String firstName;
  @Name
  @NotBlank(message = "Can't be empty!")
  private String lastName;
  @NotNull
  private LocalDate birthDate;
  @NotNull
  @Email
  private String email;
  @NotNull
  private Sex sex;
}
