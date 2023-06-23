package com.aguri.captionlive.service;

import java.util.List;

import com.aguri.captionlive.DTO.RequestRequest;
import com.aguri.captionlive.model.Request;

public interface RequestService {
    List<Request> getAllRequests();
    
    Request createRequest(RequestRequest newRequest);
    
    Request getRequestById(Long id);
    
    void deleteRequest(Long id);
    
    Request updateRequest(Long id, RequestRequest newRequest);
    
    Request markRequestAsRead(Long id, Long userId);

    Request markRequestAsUnread(Long id, Long userId);
    
    Request approveRequest(Long id);
    
    Request rejectRequest(Long id);
    
    List<Request> getAllRequestsForSenderUser(Long userId);

    List<Request> getAllRequestsForRecipientUser(Long userId);

    List<Request> getAllRequestsForUser(Long userId);
}

