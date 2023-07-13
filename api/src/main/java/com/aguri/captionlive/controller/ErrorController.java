package com.aguri.captionlive.controller;

import com.aguri.captionlive.model.Error;
import com.aguri.captionlive.service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/errors")
public class ErrorController {
    private final ErrorService errorService;

    @Autowired
    public ErrorController(ErrorService errorService) {
        this.errorService = errorService;
    }

//    @GetMapping
//    public ResponseEntity<List<Error>> getAllErrors() {
//        List<Error> errors = errorService.getAllErrors();
//        return new ResponseEntity<>(errors, HttpStatus.OK);
//    }
//
//    @GetMapping("/{errorId}")
//    public ResponseEntity<Error> getErrorById(@PathVariable Long errorId) {
//        Error error = errorService.getErrorById(errorId);
//        return new ResponseEntity<>(error, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<Error> createError(@RequestBody Error error) {
        Error createdError = errorService.createError(error);
        return new ResponseEntity<>(createdError, HttpStatus.CREATED);
    }

//    @PutMapping("/{errorId}")
//    public ResponseEntity<Error> updateError(@PathVariable Long errorId, @RequestBody Error error) {
//        Error updatedError = errorService.updateError(errorId, error);
//        return new ResponseEntity<>(updatedError, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{errorId}")
//    public ResponseEntity<Void> deleteError(@PathVariable Long errorId) {
//        errorService.deleteError(errorId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
