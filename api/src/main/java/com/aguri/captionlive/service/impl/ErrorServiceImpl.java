package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.model.Error;
import com.aguri.captionlive.repository.ErrorRepository;
import com.aguri.captionlive.service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ErrorServiceImpl implements ErrorService {
    private final ErrorRepository errorRepository;

    @Autowired
    public ErrorServiceImpl(ErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
    }

    public List<Error> getAllErrors() {
        return errorRepository.findAll();
    }

    public Error getErrorById(Long errorId) {
        Optional<Error> optionalError = errorRepository.findById(errorId);
        return optionalError.orElse(null);
    }

    public Error createError(Error error) {
        return errorRepository.save(error);
    }

    public Error updateError(Long errorId, Error updatedError) {
        Optional<Error> optionalError = errorRepository.findById(errorId);
        if (optionalError.isPresent()) {
            Error existingError = optionalError.get();
            existingError.setBody(updatedError.getBody());
            return errorRepository.save(existingError);
        }
        return null;
    }

    public void deleteError(Long errorId) {
        errorRepository.deleteById(errorId);
    }
}
