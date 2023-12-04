package com.runtracker.exception;

public class NotFoundException extends BaseException {

  public NotFoundException(Error error) {
    super(error);
  }
}
