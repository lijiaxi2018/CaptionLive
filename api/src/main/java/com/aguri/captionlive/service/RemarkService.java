package com.aguri.captionlive.service;

import com.aguri.captionlive.model.Remark;

import java.util.List;

public interface RemarkService {
    List<Remark> getAllRemarks();

    Remark createRemark(Remark remark);

    Remark getRemarkById(Long id);

    void deleteRemark(Long id);

    Remark updateRemark(Long id, Remark remark);

    Object addRemark(String content, Long userId, Long segmentId);
}
