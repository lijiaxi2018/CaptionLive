package com.aguri.captionlive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aguri.captionlive.DTO.RequestRequest;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Request;
import com.aguri.captionlive.service.RequestService;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    public ResponseEntity<Resp> getAllRequests() {
        return ResponseEntity.ok(Resp.ok(requestService.getAllRequests()));
    }

    @PostMapping
    public ResponseEntity<Resp> createRequest(@RequestBody RequestRequest newRequest) {
        return ResponseEntity.ok(Resp.ok(requestService.createRequest(newRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resp> getRequestById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Resp.ok(requestService.getRequestById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resp> deleteRequest(@PathVariable("id") Long id) {
        requestService.getRequestById(id);
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resp> updateRequest(@PathVariable("id") Long id, @RequestBody RequestRequest newRequest) {
        return ResponseEntity.ok(Resp.ok(requestService.updateRequest(id, newRequest)));
    }

    @PutMapping("/read/{id}/{userId}")
    public ResponseEntity<Resp> markRequestAsRead(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(Resp.ok(requestService.markRequestAsRead(id, userId)));
    }

    @PutMapping("/unread/{id}/{userId}")
    public ResponseEntity<Resp> markRequestAsUnread(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(Resp.ok(requestService.markRequestAsUnread(id, userId)));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Resp> approveRequest(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Resp.ok(requestService.approveRequest(id)));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Resp> rejectRequest(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Resp.ok(requestService.rejectRequest(id)));
    }

    @GetMapping("/getAllSenderRequests/{userId}")
    public ResponseEntity<Resp> getAllRequestsForSenderUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(Resp.ok(requestService.getAllRequestsForSenderUser(userId)));
    }

    @GetMapping("/getAllRecipientRequests/{userId}")
    public ResponseEntity<Resp> getAllRequestsForRecipientUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(Resp.ok(requestService.getAllRequestsForRecipientUser(userId)));
    }
}

