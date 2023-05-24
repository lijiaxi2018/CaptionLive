package com.aguri.captionlive.common.exception;

import com.aguri.captionlive.common.resp.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Resp> handleUserNotFoundException(UserNotFoundException ex) {
        Resp response = Resp.failed("[User not found]");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Resp> handleException(Exception ex) {
        Resp response = new Resp("Unknown Error", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 其他异常处理方法...

}

