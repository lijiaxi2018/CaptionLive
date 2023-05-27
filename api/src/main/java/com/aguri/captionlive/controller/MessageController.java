package com.aguri.captionlive.controller;

import com.aguri.captionlive.model.Message;
import com.aguri.captionlive.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/api/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/api/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) {
        Message createdMessage = messageService.createMessage(newMessage);
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/api/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("id") Long id) {
        Message message = messageService.getMessageById(id);
        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/messages/{id}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable("id") Long id) {
        boolean deleted = messageService.deleteMessageById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/api/messages/{id}")
    public ResponseEntity<Message> updateMessageById(@PathVariable("id") Long id, @RequestBody Message newMessage) {
        Message existingMessage = messageService.getMessageById(id);
        if (existingMessage != null) {
            Message updatedMessage = messageService.updateMessage(existingMessage, newMessage);
            return ResponseEntity.ok(updatedMessage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/messages/getAllMessages/{requestid}")
    public ResponseEntity<List<Message>> getMessagesByRequestId(@PathVariable("requestid") Long requestId) {
        List<Message> messages = messageService.getMessagesByRequestId(requestId);
        return ResponseEntity.ok(messages);
    }
}

