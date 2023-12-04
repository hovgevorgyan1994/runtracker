package com.runtracker.dto.run;

import lombok.Value;

@Value(staticConstructor = "of")
public class UserStatisticsResponse {

  public static UserStatisticsResponse EMPTY = UserStatisticsResponse.of(0L, 0.0, 0.0);

  Long totalRuns;
  Double totalDistance;
  Double averageSpeed;
}
