package com.easy.throttling.common.exceptions;

/**
 * Created by syurov on 12/5/2018.
 */
public class ThrottlingOverException extends Exception {

  public ThrottlingOverException(String message) {
    super(message);
  }

  public ThrottlingOverException(String message, Throwable cause) {
    super(message, cause);
  }
}
