package com.runtracker.dto.run;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value(staticConstructor = "of")
@EqualsAndHashCode(of = {"id", "userId", "startLatitude", "startLongitude"})
public class RunResponse {

  Long id;
  Long userId;
  Double startLatitude;
  Double startLongitude;
  LocalDateTime startDatetime;
  Double finishLatitude;
  Double finishLongitude;
  LocalDateTime finishDatetime;
  Double distance;
  Double averageSpeed;
}
