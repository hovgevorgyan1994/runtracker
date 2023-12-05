package com.runtracker.service;

import static com.runtracker.parameters.RunTestParams.finishedRun;
import static com.runtracker.parameters.RunTestParams.invalidFinishRunRequest;
import static com.runtracker.parameters.RunTestParams.validFinishRunRequest;
import static com.runtracker.parameters.RunTestParams.unfinishedRun;
import static com.runtracker.parameters.RunTestParams.runResponse;
import static com.runtracker.parameters.RunTestParams.validStartRunRequest;
import static com.runtracker.parameters.RunTestParams.userStatisticsResponse;
import static com.runtracker.parameters.UserTestParams.user;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.runtracker.dto.run.UserStatisticsResponse;
import com.runtracker.entity.Run;
import com.runtracker.exception.AlreadyFinishedException;
import com.runtracker.exception.BaseException;
import com.runtracker.mapper.RunMapper;
import com.runtracker.parameters.RunTestParams;
import com.runtracker.repository.RunRepository;
import com.runtracker.service.impl.RunServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RunServiceTest {

  @Mock
  private RunRepository runRepository;
  @Mock
  private CommonService commonService;
  @Mock
  private RunMapper runMapper;
  @InjectMocks
  private RunServiceImpl runService;

  @Test
  void shouldStartRunSuccessfully() {
    //given
    var request = validStartRunRequest();
    var user = user();
    var run = unfinishedRun(user);
    var expected = RunTestParams.runResponse();

    //when
    doReturn(user).when(commonService).getUser(any());
    doReturn(run).when(runMapper).mapToEntity(user, request);
    doReturn(run).when(runRepository).save(any());
    doReturn(expected).when(runMapper).mapToDto(run);

    var actual = runService.start(request);

    //then
    assertThat(actual)
            .isNotNull()
            .isEqualTo(expected);
  }

  @Test
  void shouldFinishRunSuccessfully() {
    //given
    var request = validFinishRunRequest();
    var run = unfinishedRun(user());
    var expected = RunTestParams.runResponse();

    //when
    doReturn(Optional.of(run)).when(runRepository).findById(any());
    doReturn(expected).when(runMapper).mapToDto(run);

    var actual = runService.finish(request);

    //then
    assertThat(actual)
            .isNotNull()
            .isEqualTo(expected);
  }

  @Test
  void shouldFailFinishRunAsRunNotFound() {
    //given
    var request = validFinishRunRequest();

    //when
    doReturn(Optional.empty()).when(runRepository).findById(any());

    //then
    assertThrows(BaseException.class,
            () -> runService.finish(request));
  }

  @Test
  void shouldFailFinishRunAsAlreadyFinished() {
    //given
    var request = invalidFinishRunRequest();
    var finishedRun = finishedRun(user());

    //when
    doReturn(Optional.of(finishedRun)).when(runRepository).findById(any());

    //then
    assertThrows(AlreadyFinishedException.class, () -> runService.finish(request));
  }

  @Test
  void shouldGetUserRuns() {
    //given
    var id = 1L;
    var runs = List.of(unfinishedRun(null));
    var expected = List.of(runResponse());

    //when
    doReturn(runs).when(runRepository).findByUserIdAndStartDatetimeBetween(any(), any(), any());
    doReturn(runResponse()).when(runMapper).mapToDto(any());

    var actual = runService.getUserRuns(id, LocalDateTime.MAX, LocalDateTime.MAX);
    //then
    assertThat(actual)
            .isNotNull()
            .isEqualTo(expected);
  }

  @Test
  void shouldGetEmptyListInGetUserRunsAsNoRunsFound() {
    //given
    var id = 1L;
    var runs = List.of();
    var expected = List.of();

    //when
    doReturn(runs).when(runRepository).findByUserIdAndStartDatetimeBetween(any(), any(), any());

    var actual = runService.getUserRuns(id, LocalDateTime.MAX, LocalDateTime.MAX);
    //then
    assertThat(actual)
            .isNotNull()
            .isEqualTo(expected);
  }

  @Test
  void shouldGetUserStatisticsSuccessfully() {
    //given
    var id = 1L;
    var runs = List.of(unfinishedRun(null));
    var expected = userStatisticsResponse();

    //when
    doReturn(runs).when(runRepository).findByUserIdAndStartDatetimeBetween(any(), any(), any());
    doReturn(runResponse()).when(runMapper).mapToDto(any());

    var actual = runService.getUserStatistics(id, LocalDateTime.MAX,
            LocalDateTime.MAX);

    //then
    assertThat(actual)
            .isNotNull()
            .isEqualTo(expected);
  }

  @Test
  void shouldGetEmptyStatisticsAsNoRun() {
    //given
    var id = 1L;
    var runs = List.of();
    var expected = UserStatisticsResponse.EMPTY;

    //when
    doReturn(runs).when(runRepository).findByUserIdAndStartDatetimeBetween(any(), any(), any());

    var actual = runService.getUserStatistics(id, LocalDateTime.MAX, LocalDateTime.MAX);

    //then
    assertThat(actual)
            .isNotNull()
            .isEqualTo(expected);
  }
}
