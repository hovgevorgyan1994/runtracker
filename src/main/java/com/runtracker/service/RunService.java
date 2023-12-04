package com.runtracker.service;

import com.runtracker.dto.run.FinishRunRequest;
import com.runtracker.dto.run.RunResponse;
import com.runtracker.dto.run.StartRunRequest;
import com.runtracker.dto.run.UserStatisticsResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface RunService {

  RunResponse start(StartRunRequest runRequest);

  RunResponse finish(FinishRunRequest runRequest);

  List<RunResponse> getUserRuns(Long userId, LocalDateTime fromDatetime, LocalDateTime toDatetime);

  UserStatisticsResponse getUserStatistics(Long userId, LocalDateTime fromDatetime,
      LocalDateTime toDatetime);
}
