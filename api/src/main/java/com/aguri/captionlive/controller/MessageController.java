package com.aguri.captionlive.controller;

import com.aguri.captionlive.DTO.MessageRequest;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Message;
import com.aguri.captionlive.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/api/messages")
    public ResponseEntity<Resp> getAllMessages() {
        return ResponseEntity.ok(Resp.ok(messageService.getAllMessages()));
    }

    @PostMapping("/api/messages")
    public ResponseEntity<Resp> createMessage(@RequestBody MessageRequest newMessage) {
        return ResponseEntity.ok(Resp.ok(messageService.createMessage(newMessage)));
    }

    @GetMapping("/api/messages/{id}")
    public ResponseEntity<Resp> getMessageById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Resp.ok(messageService.getMessageById(id)));
    }

    @DeleteMapping("/api/messages/{id}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable("id") Long id) {
        messageService.getMessageById(id);
        messageService.deleteMessageById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/messages/{id}")
    public ResponseEntity<Resp> updateMessageById(@PathVariable("id") Long id, @RequestBody MessageRequest newMessage) {
        return ResponseEntity.ok(Resp.ok(messageService.updateMessage(id, newMessage)));
    }

    @GetMapping("/api/messages/getAllMessages/{requestid}")
    public ResponseEntity<Resp> getMessagesByRequestId(@PathVariable("requestid") Long requestId) {
        return ResponseEntity.ok(Resp.ok(messageService.getMessagesByRequestId(requestId)));
    }
}

