package com.runtracker.utils;

import static lombok.AccessLevel.PRIVATE;

import com.runtracker.dto.run.RunResponse;
import com.runtracker.entity.Run;
import java.time.Duration;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class CalculationUtils {

  public static double averageSpeed(Run run) {
    double averageSpeed = 0.0;
    double durationInSeconds = Duration.between(run.getStartDatetime(), run.getFinishDatetime())
        .getSeconds();
    if (durationInSeconds > 0) {
      averageSpeed = run.getDistance() / durationInSeconds;
    }
    return averageSpeed;
  }

  public static double calculateDistance(double startLat, double startLon,
      double finishLat, double finishLon) {
    double earthRadius = 6371; // Earth radius in kilometers
    double latDistance = Math.toRadians(finishLat - startLat);
    double lonDistance = Math.toRadians(finishLon - startLon);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(finishLat))
        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return earthRadius * c * 1000; // Distance in meters
  }

  public static double averageSpeed(List<RunResponse> userRuns) {
    return userRuns.stream().mapToDouble(RunResponse::getAverageSpeed).average().orElse(0.0);
  }

  public static double totalDistance(List<RunResponse> userRuns) {
    return userRuns.stream().mapToDouble(RunResponse::getDistance).sum();
  }
}
