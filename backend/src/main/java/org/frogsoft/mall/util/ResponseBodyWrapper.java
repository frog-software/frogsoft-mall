package org.frogsoft.mall.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseBodyWrapper<T> {

  private int status = HttpStatus.OK.value();
  private String statusString = HttpStatus.OK.getReasonPhrase();
  private String message = "";
  private String error = "";
  private T data;

  public ResponseEntity<ResponseBodyWrapper<T>> build() {
    return ResponseEntity.status(status).body(this);
  }

  public ResponseBodyWrapper<T> message(String msg) {
    this.message = msg;
    return this;
  }

  public ResponseBodyWrapper<T> error(String msg) {
    this.error = msg;
    return this;
  }

  public ResponseBodyWrapper<T> body(T body) {
    this.data = body;
    return this;
  }

  public ResponseBodyWrapper<T> status(HttpStatus status) {
    this.status = status.value();
    this.statusString = status.getReasonPhrase();
    return this;
  }

  public ResponseBodyWrapper<T> status(int statusCode) {
    HttpStatus httpStatus = HttpStatus.resolve(statusCode);
    assert httpStatus != null;
    status(httpStatus);
    return this;
  }
}
