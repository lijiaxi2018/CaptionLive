package com.aguri.captionlive.service;

import com.aguri.captionlive.model.Access;

import java.util.List;

public interface AccessService {

    List<Access> getAllAccesses();

    Access getAccessById(Long id);

    Access createAccess(Access access);

    Access updateAccess(Long id, Access access);

    void deleteAccess(Long id);

}