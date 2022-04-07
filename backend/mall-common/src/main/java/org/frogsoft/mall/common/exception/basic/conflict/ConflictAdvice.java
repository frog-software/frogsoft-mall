package org.frogsoft.mall.common.exception.basic.conflict;

import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConflictAdvice {

  @ResponseBody
  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseBodyWrapper<?> conflictAdvice(ConflictException ex) {
    return new ResponseBodyWrapper<String>()
        .status(HttpStatus.CONFLICT)
        .body(ex.getMessage())
        .message(ex.getMessage());
  }
}
