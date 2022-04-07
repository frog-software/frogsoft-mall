package org.frogsoft.mall.common.exception.basic.notfound;

import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseBodyWrapper<?> notFoundException(NotFoundException ex) {
    return new ResponseBodyWrapper<String>()
        .status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage())
        .message(ex.getMessage());
  }
}
