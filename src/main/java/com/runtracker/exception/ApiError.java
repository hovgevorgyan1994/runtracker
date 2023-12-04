package com.runtracker.exception;

import java.time.LocalDateTime;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value(staticConstructor = "of")
public class ApiError {

  HttpStatus status;
  Integer errorCode;
  LocalDateTime timestamp;
  String message;
}