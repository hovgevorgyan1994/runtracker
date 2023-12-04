package com.runtracker.dto.user;

import com.runtracker.validation.Name;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EditUserRequest {

  @Name
  private String firstName;
  @Name
  private String lastName;
  private LocalDate birthDate;
  private Sex sex;
}
