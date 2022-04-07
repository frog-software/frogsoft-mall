package org.frogsoft.mall.auth.exception.basic.badrequest;

import org.frogsoft.mall.auth.util.ResponseBodyWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BadRequestAdvice {

  @ResponseBody
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseBodyWrapper<?> badRequestAdvice(BadRequestException ex) {
    return new ResponseBodyWrapper<String>()
        .status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage())
        .message(ex.getMessage());
  }
}
