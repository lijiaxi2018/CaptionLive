package com.aguri.captionlive.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aguri.captionlive.DTO.RequestRequest;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.common.util.TimeComparator;
import com.aguri.captionlive.model.Request;
import com.aguri.captionlive.repository.RequestRepository;
import com.aguri.captionlive.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public Request createRequest(RequestRequest newRequest) {
        Request request = new Request();
        request.setBody(newRequest.getBody());
        request.setRecipient(newRequest.getRecipient());
        request.setRecipientRead(newRequest.isRecipientRead());
        request.setSender(newRequest.getSender());
        request.setSenderRead(newRequest.isSenderRead());
        request.setStatus(newRequest.getStatus());
        request.setType(newRequest.getType());
        return requestRepository.save(request);
    }

    @Override
    public Request getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request not found with id: " + id));
    }

    @Override
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public Request updateRequest(Long id, RequestRequest newRequest) {
        Request existingRequest = getRequestById(id);
        //Need to confirm what attributes should be update
        existingRequest.setStatus(newRequest.getStatus());
        existingRequest.setSenderRead(newRequest.isSenderRead());
        existingRequest.setRecipientRead(newRequest.isRecipientRead());
        existingRequest.setSender(newRequest.getSender());
        existingRequest.setRecipient(newRequest.getRecipient());
        existingRequest.setBody(newRequest.getBody());
        return requestRepository.save(existingRequest);
    }

    @Override
    public Request markRequestAsRead(Long id, Long userId) {

        Request existingRequest = getRequestById(id);

        if(userId == existingRequest.getSender()){
            existingRequest.setSenderRead(true);
        }
        else if(userId == existingRequest.getRecipient()){
            existingRequest.setRecipientRead( true);
        }
        else{
            throw new RuntimeException("UserId doesn't match request!");
        }
        return requestRepository.save(existingRequest);
    }

    @Override
    public Request markRequestAsUnread(Long id, Long userId) {

        Request existingRequest = getRequestById(id);

        if(userId == existingRequest.getSender()){
            existingRequest.setSenderRead(false);
        }
        else if(userId == existingRequest.getRecipient()){
            existingRequest.setRecipientRead( false);
        }
        else{
            throw new RuntimeException("UserId doesn't match request!");
        }
        return requestRepository.save(existingRequest);
    }

    @Override
    public Request approveRequest(Long id) {
        Request existingRequest = getRequestById(id);
        //Need to confirm what attributes should be update
        // pending  0
        // Approved 1
        // Rejected 2
        existingRequest.setStatus(1);
        return requestRepository.save(existingRequest);
    }

    @Override
    public Request rejectRequest(Long id) {
        Request existingRequest = getRequestById(id);
        //Need to confirm what attributes should be update
        // pending  0
        // Approved 1
        // Rejected 2
        existingRequest.setStatus(2);
        return requestRepository.save(existingRequest);
    }

    @Override
    public List<Request> getAllRequestsForSenderUser(Long userId) {
        List<Request> requestsBySender = requestRepository.findAllBySender(userId);
        return requestsBySender;
    }

    @Override
    public List<Request> getAllRequestsForRecipientUser(Long userId) {
        List<Request> requestsByRecipient = requestRepository.findAllByRecipient(userId);
        return requestsByRecipient;
    }

    @Override
    public List<Request> getAllRequestsForUser(Long userId) {
        List<Request> requestsBySender = requestRepository.findAllBySender(userId);
        List<Request> requestsByRecipient = requestRepository.findAllByRecipient(userId);
        List<Request> mergedRequests = new ArrayList<>();
        mergedRequests.addAll(requestsBySender);
        mergedRequests.addAll(requestsByRecipient);
        Collections.sort(mergedRequests, new TimeComparator());
        return mergedRequests;
    }

}
