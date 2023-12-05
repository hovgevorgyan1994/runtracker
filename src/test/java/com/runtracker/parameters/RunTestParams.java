package com.runtracker.parameters;

import static com.runtracker.parameters.UserTestParams.user;
import static java.lang.Double.MAX_VALUE;
import static lombok.AccessLevel.PRIVATE;

import com.runtracker.dto.run.FinishRunRequest;
import com.runtracker.dto.run.RunResponse;
import com.runtracker.dto.run.StartRunRequest;
import com.runtracker.dto.run.UserStatisticsResponse;
import com.runtracker.entity.Run;
import com.runtracker.entity.User;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class RunTestParams {

  public static StartRunRequest validStartRunRequest() {
    return StartRunRequest.builder()
            .userId(1L)
            .startLatitude(MAX_VALUE)
            .startLongitude(MAX_VALUE)
            .build();
  }

  public static StartRunRequest invalidStartRunRequest() {
    return StartRunRequest.builder()
            .startLatitude(MAX_VALUE)
            .startLongitude(MAX_VALUE)
            .build();
  }

  public static FinishRunRequest validFinishRunRequest() {
    return FinishRunRequest.builder()
            .runId(2L)
            .finishLatitude(MAX_VALUE)
            .finishLongitude(MAX_VALUE)
            .build();
  }

  public static FinishRunRequest invalidFinishRunRequest() {
    return FinishRunRequest.builder()
            .runId(1L)
            .finishLatitude(MAX_VALUE)
            .finishLongitude(MAX_VALUE)
            .build();
  }

  public static Run unfinishedRun(User user) {
    return Run.builder()
            .id(2L)
            .user(user)
            .distance(100.0)
            .startDatetime(LocalDateTime.now())
            .startLatitude(MAX_VALUE)
            .startLongitude(MAX_VALUE)
            .build();
  }
  public static Run finishedRun(User user) {
    return Run.builder()
            .id(1L)
            .user(user)
            .distance(100.0)
            .startDatetime(LocalDateTime.now())
            .startLatitude(MAX_VALUE)
            .startLongitude(MAX_VALUE)
            .finishDatetime(LocalDateTime.MAX)
            .finishLatitude(MAX_VALUE)
            .finishLongitude(MAX_VALUE)
            .build();
  }

  public static RunResponse runResponse() {
    return RunResponse.of(1L,
            1L, MAX_VALUE, MAX_VALUE,
            LocalDateTime.MAX,
            MAX_VALUE, MAX_VALUE,
            LocalDateTime.MAX, MAX_VALUE,
            100.0);
  }

  public static RunResponse runResponse(StartRunRequest request) {
    return RunResponse.of(2L, request.getUserId(), request.getStartLatitude(),
            request.getStartLongitude(),
            LocalDateTime.now(), null,
            null, null, null, null);
  }

  public static RunResponse runResponse(FinishRunRequest request) {
    var run = unfinishedRun(user());
    return RunResponse.of(1L, run.getUser().getId(), run.getStartLatitude(),
            run.getStartLongitude(), run.getStartDatetime(), request.getFinishLatitude(),
            validFinishRunRequest().getFinishLongitude(), LocalDateTime.now(), null, null);
  }

  public static UserStatisticsResponse userStatisticsResponse() {
    return UserStatisticsResponse.of(1L, MAX_VALUE, 100.0);
  }
}
