package com.runtracker.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.runtracker.dto.run.FinishRunRequest;
import com.runtracker.dto.run.RunResponse;
import com.runtracker.dto.run.StartRunRequest;
import com.runtracker.dto.run.UserStatisticsResponse;
import com.runtracker.service.RunService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/runs")
@RequiredArgsConstructor
public class RunController {

  private final RunService runService;

  @PostMapping("/start")
  public ResponseEntity<RunResponse> start(@RequestBody @Valid StartRunRequest runRequest) {
    return ResponseEntity.status(CREATED).body(runService.start(runRequest));
  }

  @PostMapping("/finish")
  public ResponseEntity<RunResponse> finish(@RequestBody @Valid FinishRunRequest finishRunRequest) {
    return ResponseEntity.status(CREATED).body(runService.finish(finishRunRequest));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<List<RunResponse>> getUserRuns(
      @PathVariable Long userId,
      @RequestParam(required = false, defaultValue = "2022-01-01T00:00:00") LocalDateTime fromDatetime,
      @RequestParam(required = false, defaultValue = "2100-12-31T23:59:59") LocalDateTime toDatetime) {
    return ResponseEntity.ok(runService.getUserRuns(userId, fromDatetime, toDatetime));
  }

  @GetMapping("/statistics/{userId}")
  public ResponseEntity<UserStatisticsResponse> getUserStatistics(
      @PathVariable Long userId,
      @RequestParam(required = false, defaultValue = "2022-01-01T00:00:00") LocalDateTime fromDatetime,
      @RequestParam(required = false, defaultValue = "2100-12-31T23:59:59") LocalDateTime toDatetime) {
    return ResponseEntity.ok(runService.getUserStatistics(userId, fromDatetime, toDatetime));
  }
}
