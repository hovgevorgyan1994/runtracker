package com.runtracker.exception;

public class UserAlreadyExistException extends BaseException {

  public UserAlreadyExistException(Error error) {
    super(error);
  }
}
