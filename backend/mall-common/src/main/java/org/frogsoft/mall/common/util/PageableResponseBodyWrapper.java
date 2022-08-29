package org.frogsoft.mall.common.util;

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
public class PageableResponseBodyWrapper<T> {

    private int status = HttpStatus.OK.value();
    private int page;
    private int size;
    private String statusString = HttpStatus.OK.getReasonPhrase();
    private String message = "";
    private String error = "";
    private T data;

    public ResponseEntity<PageableResponseBodyWrapper<T>> build() {
        return ResponseEntity.status(status).body(this);
    }

    public PageableResponseBodyWrapper<T> page(int page) {
        this.page = page;
        return this;
    }

    public PageableResponseBodyWrapper<T> size(int size) {
        this.size = size;
        return this;
    }

    public PageableResponseBodyWrapper<T> message(String msg) {
        this.message = msg;
        return this;
    }

    public PageableResponseBodyWrapper<T> error(String msg) {
        this.error = msg;
        return this;
    }

    public PageableResponseBodyWrapper<T> body(T body) {
        this.data = body;
        return this;
    }

    public PageableResponseBodyWrapper<T> status(HttpStatus status) {
        this.status = status.value();
        this.statusString = status.getReasonPhrase();
        return this;
    }

    public PageableResponseBodyWrapper<T> status(int statusCode) {
        HttpStatus httpStatus = HttpStatus.resolve(statusCode);
        assert httpStatus != null;
        status(httpStatus);
        return this;
    }
}
