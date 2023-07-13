package com.aguri.captionlive.service;

import com.aguri.captionlive.model.Error;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ErrorService {

    List<Error> getAllErrors();

    Error getErrorById(Long errorId);

    Error createError(Error error);

    Error updateError(Long errorId, Error updatedError);

    void deleteError(Long errorId);
}
