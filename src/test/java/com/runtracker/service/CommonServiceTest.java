package com.runtracker.service;

import static com.runtracker.exception.Error.USER_NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.runtracker.exception.BaseException;
import com.runtracker.parameters.UserTestParams;
import com.runtracker.repository.UserRepository;
import com.runtracker.service.impl.CommonServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommonServiceTest {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private CommonServiceImpl commonService;

  @Test
  void shouldGetUserSuccessfully() {
    //given
    var id = 1L;
    var expected = UserTestParams.user();

    //when
    doReturn(Optional.of(expected)).when(userRepository).findById(any());

    var actual = commonService.getUser(id);

    //then
    assertThat(actual)
        .isNotNull()
        .isEqualTo(expected);

  }

  @Test
  void shouldFailAsUserNotFound() {
    //given
    var id = 1L;

    //when
    doReturn(Optional.empty()).when(userRepository).findById(any());

    //then
    var exception = assertThrows(BaseException.class, () -> commonService.getUser(id));
    assertEquals(USER_NOT_FOUND, exception.getError());
  }
}
