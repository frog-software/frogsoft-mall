package org.frogsoft.mall.common.exception.basic.unauthorized;

import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UnauthorizedAdvice {

  @ResponseBody
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseBodyWrapper<?> unauthorizedAdvice(UnauthorizedException ex) {

    return new ResponseBodyWrapper<String>()
        .status(HttpStatus.UNAUTHORIZED)
        .body(ex.getMessage())
        .message(ex.getMessage());
  }
}
