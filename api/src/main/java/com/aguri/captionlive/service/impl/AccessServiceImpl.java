package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.Access;
import com.aguri.captionlive.repository.AccessRepository;
import com.aguri.captionlive.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    private AccessRepository accessRepository;

    @Override
    public List<Access> getAllAccesses() {
        return accessRepository.findAll();
    }

    @Override
    public Access getAccessById(Long id) {
        return accessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Access not found with id: " + id));
    }

    @Override
    public Access createAccess(Access access) {
        return accessRepository.save(access);
    }

    @Override
    public Access updateAccess(Long id, Access access) {
        Access existingAccess = accessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Access not found with id: " + id));
        existingAccess.setPermission(access.getPermission());
        existingAccess.setCommitment(access.getCommitment());
        return accessRepository.save(existingAccess);
    }

    @Override
    public void deleteAccess(Long id) {
        accessRepository.deleteById(id);
    }
}
