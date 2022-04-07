package org.frogsoft.mall.auth.exception.basic.forbidden;

import org.frogsoft.mall.auth.util.ResponseBodyWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ForbiddenAdvice {

  @ResponseBody
  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseBodyWrapper<?> forbiddenException(ForbiddenException ex) {
    return new ResponseBodyWrapper<String>()
        .status(HttpStatus.FORBIDDEN)
        .body(ex.getMessage())
        .message(ex.getMessage());
  }
}
