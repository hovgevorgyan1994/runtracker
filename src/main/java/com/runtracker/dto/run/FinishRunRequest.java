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
public class FinishRunRequest {

  @NotNull
  private Long runId;
  @NotNull
  private Double finishLatitude;
  @NotNull
  private Double finishLongitude;
}
