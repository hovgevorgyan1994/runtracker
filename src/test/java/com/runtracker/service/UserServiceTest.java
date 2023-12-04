package com.runtracker.service;

import static com.runtracker.parameters.UserTestParams.validCreateUserRequest;
import static com.runtracker.parameters.UserTestParams.editUserRequest;
import static com.runtracker.parameters.UserTestParams.user;
import static com.runtracker.parameters.UserTestParams.userResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import com.runtracker.entity.User;
import com.runtracker.exception.BaseException;
import com.runtracker.mapper.UserMapper;
import com.runtracker.repository.UserRepository;
import com.runtracker.service.impl.UserServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private CommonService commonService;
  @Mock
  private UserMapper userMapper;
  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void shouldRegisterUserSuccessfully() {
    //given
    var request = validCreateUserRequest();
    var user = user();
    var expected = userResponse();

    //when
    doReturn(false).when(userRepository).existsByEmail(anyString());
    doReturn(user).when(userMapper).mapToEntity(request);
    doReturn(user).when(userRepository).save(user);
    doReturn(expected).when(userMapper).mapToDto(user);

    var actual = userService.register(request);

    //then
    assertThat(actual)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  void shouldNotRegisterAsDuplicateEmail() {
    //given
    var request = validCreateUserRequest();

    //when
    doReturn(true).when(userRepository).existsByEmail(anyString());

    //then
    assertThrows(BaseException.class, () -> userService.register(request));
  }


  @Test
  void shouldEditUserSuccessfully() {
    //given
    var userid = 1L;
    var request = editUserRequest();
    var user = user();
    var expected = userResponse();

    //when
    doReturn(user).when(commonService).getUser(userid);
    doReturn(expected).when(userMapper).mapToDto(user);

    var actual = userService.edit(userid, request);

    //then
    assertThat(actual)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  void shouldGetUserById() {
    //given
    var userid = 1L;
    var user = user();
    var expected = userResponse();

    //when
    doReturn(user).when(commonService).getUser(userid);
    doReturn(expected).when(userMapper).mapToDto(user);

    var actual = userService.getById(userid);

    //then
    assertThat(actual)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  void shouldGetAllUsers() {
    //given
    var users = List.of(user());
    var expected = userResponse();

    //when
    doReturn(users).when(userRepository).findAll();
    doReturn(expected).when(userMapper).mapToDto(any(User.class));

    var actual = userService.getAll();

    //then
    assertThat(actual)
        .isNotNull()
        .isEqualTo(List.of(expected));
  }
}
