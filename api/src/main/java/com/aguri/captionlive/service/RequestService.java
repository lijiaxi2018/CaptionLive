package com.aguri.captionlive.service;

import java.util.List;

import com.aguri.captionlive.model.Request;

public interface RequestService {
    List<Request> getAllRequests();
    
    Request createRequest(Request newRequest);
    
    Request getRequestById(Long id);
    
    void deleteRequest(Long id);
    
    Request updateRequest(Long id, Request newRequest);
    
    Request markRequestAsRead(Long id, Long userId);
    
    Request approveRequest(Long id);
    
    Request rejectRequest(Long id);
    
    List<Request> getAllRequestsForUser(Long userId);
}

