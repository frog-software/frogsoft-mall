package org.frogsoft.mall.common.exception.basic.unauthorized;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String message) {
    super(message);
  }
}
