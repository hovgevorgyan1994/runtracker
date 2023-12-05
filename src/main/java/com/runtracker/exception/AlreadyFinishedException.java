package com.runtracker.exception;

public class AlreadyFinishedException extends BaseException {

  public AlreadyFinishedException(Error error) {
    super(error);
  }
}
