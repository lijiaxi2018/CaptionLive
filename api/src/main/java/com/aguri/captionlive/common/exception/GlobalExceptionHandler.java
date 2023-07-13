package com.aguri.captionlive.common.exception;

import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Error;
import com.aguri.captionlive.service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    @Autowired
    ErrorService errorService;


    @ExceptionHandler(ReturnErrorMessageException.class)
    public ResponseEntity<Resp> handleReturnErrorMessageException(ReturnErrorMessageException ex) {
        return ResponseEntity.ok().body(Resp.failed(ex.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Resp> handleException(Exception ex) {
        return ResponseEntity.ok().body(Resp.failed(ex.getMessage()));
    }
}

