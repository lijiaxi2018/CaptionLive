package com.aguri.captionlive.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.aguri.captionlive.model.*;
import com.aguri.captionlive.repository.AccessRepository;
import com.aguri.captionlive.repository.OrganizationRepository;
import com.aguri.captionlive.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aguri.captionlive.DTO.RequestRequest;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.common.util.TimeComparator;
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

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccessRepository accessRepository;

    @Override
    @Transactional
    public Request approveRequest(Long id) {
//        Request existingRequest = getRequestById(id);
//        //Need to confirm what attributes should be update
//        // pending  0
//        // Approved 1
//        // Rejected 2
//        existingRequest.setStatus(1);
        Long requestId = id;
        Request existingRequest = requestRepository.getReferenceById(requestId);
        var type = existingRequest.getType();
        if (type != Request.Type.ANNOUNCEMENT) {
            String body = existingRequest.getBody();
            String body1 = "{\"userId\":2,\"organizationId\":1}";
            System.out.println(Objects.equals(body, body1));
            body = body1;
            Gson gson = new Gson();
            JsonElement element = gson.fromJson(body, JsonElement.class);
            JsonObject jsonObject = element.getAsJsonObject();
            switch (type) {
                case ADD_USER_TO_ORGANIZATION -> {
                    long organizationId = jsonObject.get("organizationId").getAsLong();
                    long userId = jsonObject.get("userId").getAsLong();
                    addUser2Organization(organizationId, userId);
                }
                case SHARE_PROJECT_TO_USER, SHARE_PROJECT_TO_ORGANIZATION -> {
                    throw new RuntimeException("do not support this request type: " + type.name());
                }
                default -> throw new RuntimeException("mis-match any request types: " + type.name());
            }
        }
        existingRequest.setStatus(1);
        return requestRepository.save(existingRequest);
    }

    private void addUser2Organization(long organizationId, long userId) {
        Membership membership = new Membership();
        Organization organization = organizationRepository.getReferenceById(organizationId);
        membership.setOrganization(organization);
        User user = userRepository.getReferenceById(userId);
        membership.setUser(user);
        membership.setPermission(Membership.Permission.MEMBER);
        List<Project> projects = organization.getProjects();
        List<Access> accessList = projects.stream().map(project -> {
            Access access = new Access();
            access.setProject(project);
            access.setCommitment(Access.Commitment.NONE);
            access.setUser(user);
            access.setPermission(Access.Permission.Editable);
            return access;
        }).toList();
        accessRepository.saveAll(accessList);
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
