package com.runtracker.dto.run;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartRunRequest {

  @NotNull
  private Long userId;
  @NotNull
  private Double startLatitude;
  @NotNull
  private Double startLongitude;
}
