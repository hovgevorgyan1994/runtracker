package com.runtracker.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Error {

  CONSTRAINT_VIOLATION(BAD_REQUEST, 4001, "There is an invalid value in user input."),
  USER_ALREADY_EXISTS(CONFLICT, 4091, "There is a user registered with such email."),
  USER_NOT_FOUND(NOT_FOUND, 4041, "There is no user with given id."),
  RUN_NOT_FOUND(NOT_FOUND, 4042, "There is no run with given id.");

  private final HttpStatus httpStatus;
  private final Integer code;
  private final String message;

}
