package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.Remark;
import com.aguri.captionlive.repository.RemarkRepository;
import com.aguri.captionlive.service.RemarkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemarkServiceImpl implements RemarkService {

    @Autowired
    private RemarkRepository remarkRepository;

    @Override
    public List<Remark> getAllRemarks() {
        return remarkRepository.findAll();
    }

    @Override
    public Remark createRemark(Remark remark) {
        return remarkRepository.save(remark);
    }

    @Override
    public Remark getRemarkById(Long id) {
        return remarkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Remark not found with id: " + id));
    }

    @Override
    public void deleteRemark(Long id) {
        remarkRepository.deleteById(id);
    }

    @Override
    public Remark updateRemark(Long id, Remark remark) {
        Remark exitingRemark = getRemarkById(id);
        exitingRemark.setContent(remark.getContent());
        return remarkRepository.save(exitingRemark);
    }
}
