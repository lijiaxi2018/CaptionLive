package com.aguri.captionlive.common.exception;

import com.aguri.captionlive.common.resp.Resp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Resp> handleUserNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.ok().body(Resp.failed(ex.getMessage()));
    }

    @ExceptionHandler(OperationNotAllowException.class)
    public ResponseEntity<Resp> handleOperationNotAllowException(OperationNotAllowException ex) {
        return ResponseEntity.ok().body(Resp.failed(ex.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Resp> handleException(Exception ex) {
        return ResponseEntity.ok().body(Resp.failed(ex.getMessage()));
    }
}

