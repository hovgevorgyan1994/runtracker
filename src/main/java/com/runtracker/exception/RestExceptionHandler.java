package com.runtracker.exception;

import static java.time.LocalDateTime.now;

import io.micrometer.common.lang.NonNull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String ERROR_CODE = "errorCode";
  private static final String TIMESTAMP = "timestamp";
  private static final String STATUS = "status";
  private static final String MESSAGE = "message";
  private static final String ERRORS = "errors";

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    var error = Error.CONSTRAINT_VIOLATION;
    var body = new LinkedHashMap<>(Map.<String, Object>of(
        ERROR_CODE, error.getCode(),
        TIMESTAMP, now(),
        STATUS, error.getHttpStatus(),
        MESSAGE, error.getMessage()));
    var errors = ex.getBindingResult().getFieldErrors().stream()
        .filter(fieldError -> fieldError.getDefaultMessage() != null)
        .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    body.put(ERRORS, errors);
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler({BaseException.class})
  public ResponseEntity<ApiError> handleIllegalArgument(BaseException ex) {
    var error = ex.getError();
    var apiError = ApiError.of(error.getHttpStatus(), error.getCode(), now(), error.getMessage());
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }
}