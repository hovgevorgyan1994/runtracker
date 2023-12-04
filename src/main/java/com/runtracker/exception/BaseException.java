package com.runtracker.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

  protected final Error error;

  public BaseException(Error error) {
    this.error = error;
  }
}