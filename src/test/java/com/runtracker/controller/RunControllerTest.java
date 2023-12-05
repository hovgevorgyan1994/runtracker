package com.runtracker.controller;

import static com.runtracker.parameters.RunTestParams.finishedRun;
import static com.runtracker.parameters.RunTestParams.invalidStartRunRequest;
import static com.runtracker.parameters.RunTestParams.unfinishedRun;
import static com.runtracker.parameters.RunTestParams.runResponse;
import static com.runtracker.parameters.RunTestParams.validFinishRunRequest;
import static com.runtracker.parameters.RunTestParams.validStartRunRequest;
import static com.runtracker.parameters.UserTestParams.user;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runtracker.dto.run.RunResponse;
import com.runtracker.repository.RunRepository;
import com.runtracker.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
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
public class RunControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RunRepository runRepository;


  @BeforeEach
  void setUp() {
    var user = user();
    userRepository.save(user);
    runRepository.save(finishedRun(user));
  }

  @AfterEach
  void cleanUp() {
    runRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  void shouldStartRunSuccessfully() throws Exception {
    var request = validStartRunRequest();
    var expected = runResponse(request);

    var mvcResult = mockMvc.perform(post("/runs/start")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn();

    var actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            RunResponse.class);

    assertThat(actual)
            .isNotNull()
            .isEqualTo(expected);
  }

  @Test
  void shouldFailStartRunAsInvalidRequest() throws Exception {
    var request = invalidStartRunRequest();

    mockMvc.perform(post("/runs/start")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
  }

  @Test
  void shouldFinishRunSuccessfully() throws Exception {
    var request = validFinishRunRequest();
    runRepository.save(unfinishedRun(user()));

    mockMvc.perform(post("/runs/finish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
  }

  @Test
  void shouldFailFinishRunAsInvalidInput() throws Exception {
    var request = invalidStartRunRequest();

    mockMvc.perform(post("/runs/finish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldGetUserRuns() throws Exception {
    var userId = 1L;
    var expected = List.of(runResponse());

    var mvcResult = mockMvc.perform(get("/runs/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();

    var actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            new TypeReference<List<RunResponse>>() {
            });

    assertThat(actual)
            .isNotNull()
            .isEqualTo(expected);
  }

  @Test
  public void shouldGetUserStatistics() throws Exception {
    var userId = 1L;

    mockMvc.perform(get("/runs/statistics/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }
}
