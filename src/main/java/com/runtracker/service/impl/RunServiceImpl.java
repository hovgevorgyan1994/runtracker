package com.runtracker.service.impl;

import static com.runtracker.exception.Error.RUN_ALREADY_FINISHED;
import static com.runtracker.exception.Error.RUN_NOT_FOUND;
import static com.runtracker.utils.CalculationUtils.averageSpeed;
import static com.runtracker.utils.CalculationUtils.calculateDistance;
import static com.runtracker.utils.CalculationUtils.totalDistance;
import static java.util.Collections.emptyList;

import com.runtracker.dto.run.FinishRunRequest;
import com.runtracker.dto.run.RunResponse;
import com.runtracker.dto.run.StartRunRequest;
import com.runtracker.dto.run.UserStatisticsResponse;
import com.runtracker.exception.AlreadyFinishedException;
import com.runtracker.exception.NotFoundException;
import com.runtracker.mapper.RunMapper;
import com.runtracker.repository.RunRepository;
import com.runtracker.service.CommonService;
import com.runtracker.service.RunService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RunServiceImpl implements RunService {

  private final RunRepository runRepository;
  private final CommonService commonService;
  private final RunMapper runMapper;

  @Override
  public RunResponse start(StartRunRequest request) {
    log.info("Attempt to start a Run. {}", request);
    var user = commonService.getUser(request.getUserId());
    var run = runRepository.save(runMapper.mapToEntity(user, request));
    log.info("Successfully started a Run. {}", run);
    return runMapper.mapToDto(run);
  }

  @Override
  public RunResponse finish(FinishRunRequest request) {
    log.info("Attempt to finish a Run. {}", request);
    var run = runRepository.findById(request.getRunId())
            .orElseThrow(() -> {
              log.info("No Run was found with ID: {}", request.getRunId());
              return new NotFoundException(RUN_NOT_FOUND);
            });
    if (run.isFinished()) {
      log.warn("You cannot finished the already finished Run.");
      throw new AlreadyFinishedException(RUN_ALREADY_FINISHED);
    }
    //set operations will change the persistent entity after the commit
    //because the method gets executed in a Spring Transaction
    run.setFinishLatitude(request.getFinishLatitude());
    run.setFinishLongitude(request.getFinishLongitude());
    run.setFinishDatetime(LocalDateTime.now());
    run.setDistance(
            calculateDistance(run.getStartLatitude(), run.getStartLongitude(),
                    run.getFinishLatitude(), run.getFinishLongitude()));
    log.info("Successfully finished a Run. {}", run);
    return runMapper.mapToDto(run);
  }

  @Override
  public List<RunResponse> getUserRuns(Long userId, LocalDateTime fromDatetime,
                                       LocalDateTime toDatetime) {
    log.info("Attempt to get all User Runs. ID: {}", userId);
    var runs = runRepository.findByUserIdAndStartDatetimeBetween(userId, fromDatetime, toDatetime);
    if (runs.isEmpty()) {
      log.info("No Runs were found for user with ID: {}", userId);
      return emptyList();
    }
    log.info("{} Runs were found for user with ID: {}", runs.size(), userId);
    return runs.stream()
            .map(runMapper::mapToDto)
            .toList();
  }

  @Override
  public UserStatisticsResponse getUserStatistics(Long userId, LocalDateTime fromDatetime,
                                                  LocalDateTime toDatetime) {
    log.info("Attempt to get User Statistics. ID: {}", userId);
    var runs = getUserRuns(userId, fromDatetime, toDatetime);
    if (runs.isEmpty()) {
      log.info("No statistics found for user ID: {}", userId);
      return UserStatisticsResponse.EMPTY;
    }
    long totalRuns = runs.size();
    var response = UserStatisticsResponse.of(totalRuns, totalDistance(runs), averageSpeed(runs));
    log.info("Statistics calculated for user ID {}. Result: {}", userId, response);
    return response;
  }
}
