package com.runtracker.repository;

import com.runtracker.entity.Run;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunRepository extends JpaRepository<Run, Long> {

  List<Run> findByUserIdAndStartDatetimeBetween(Long userId, LocalDateTime fromDatetime,
      LocalDateTime toDatetime);

  List<Run> findByUserId(Long userId);
}