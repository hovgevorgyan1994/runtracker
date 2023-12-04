package com.runtracker.controller;

import static com.runtracker.parameters.UserTestParams.editUserRequest;
import static com.runtracker.parameters.UserTestParams.editedUserResponse;
import static com.runtracker.parameters.UserTestParams.invalidCreateUserRequest;
import static com.runtracker.parameters.UserTestParams.user;
import static com.runtracker.parameters.UserTestParams.userResponse;
import static com.runtracker.parameters.UserTestParams.validCreateUserRequest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runtracker.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void cleanUp() {
    userRepository.deleteAll();
  }


  @Test
  void shouldRegisterUserSuccessfully() throws Exception{
    var request = validCreateUserRequest();
    var expected = userResponse();

   mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
  }

  @Test
  void shouldFailRegisterUserAsInvalidInput() throws Exception{
    var request = invalidCreateUserRequest();

   mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldEditUserSuccessfully() throws Exception{
    var userId = 1L;
    var request = editUserRequest();
    var expected = editedUserResponse();

    userRepository.save(user());

    mockMvc.perform(put("/users/{userId}",userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
  }

  @Test
  void shouldFailEditUserAsUserNotFound() throws Exception{
    var userId = 1L;
    var request = editUserRequest();

    mockMvc.perform(put("/users/{userId}",userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldGetUserByIdSuccessfully() throws Exception{
    var userId = 1L;
    var expected = userResponse();

    userRepository.save(user());

    mockMvc.perform(get("/users/{userId}",userId))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
  }

  @Test
  void shouldNotGetUserByIdAsNotFound() throws Exception{
    var userId = 1L;

    mockMvc.perform(get("/users/{userId}",userId))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldGetAllUsers() throws Exception{
    var expected = List.of(userResponse());

    userRepository.save(user());

    mockMvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
  }

  @Test
  void shouldGetEmptyListAsNoUser() throws Exception{
    var expected = List.of();

    mockMvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
  }

  @Test
  void shouldRemoveUserSuccessfully() throws Exception{
    var userId = 1L;

    userRepository.save(user());

    mockMvc.perform(delete("/users/{userId}",userId))
        .andExpect(status().isOk());
  }

  @Test
  void shouldFailRemoveUserAsNotFound() throws Exception{
    var userId = 1L;

    mockMvc.perform(delete("/users/{userId}",userId))
        .andExpect(status().isNotFound());
  }
}
