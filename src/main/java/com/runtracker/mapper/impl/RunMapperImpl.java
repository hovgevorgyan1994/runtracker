package com.runtracker.mapper.impl;

import static com.runtracker.utils.CalculationUtils.averageSpeed;
import static java.time.LocalDateTime.now;

import com.runtracker.dto.run.RunResponse;
import com.runtracker.dto.run.StartRunRequest;
import com.runtracker.entity.Run;
import com.runtracker.entity.User;
import com.runtracker.mapper.RunMapper;
import org.springframework.stereotype.Component;

@Component
public class RunMapperImpl implements RunMapper {

  @Override
  public Run mapToEntity(User user, StartRunRequest runRequest) {
    Run run = new Run();
    run.setUser(user);
    run.setStartLatitude(runRequest.getStartLatitude());
    run.setStartLongitude(runRequest.getStartLongitude());
    run.setStartDatetime(now());
    return run;
  }

  @Override
  public RunResponse mapToDto(Run run) {
    return RunResponse.of(
        run.getId(),
        run.getUser().getId(),
        run.getStartLatitude(),
        run.getStartLongitude(),
        run.getStartDatetime(),
        run.getFinishLatitude(),
        run.getFinishLongitude(),
        run.getFinishDatetime(),
        run.getDistance(),
        run.isFinished() ? averageSpeed(run) : null);
  }
}
