package com.runtracker.mapper;

import com.runtracker.dto.run.RunResponse;
import com.runtracker.dto.run.StartRunRequest;
import com.runtracker.entity.Run;
import com.runtracker.entity.User;

public interface RunMapper {

  Run mapToEntity(User user, StartRunRequest runRequest);

  RunResponse mapToDto(Run save);
}
