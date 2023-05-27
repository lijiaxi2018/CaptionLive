package com.aguri.captionlive.service.impl;
import com.aguri.captionlive.model.Message;

import com.aguri.captionlive.common.exception.UserNotFoundException;
import com.aguri.captionlive.repository.MessageRepository;
import com.aguri.captionlive.service.MessageService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message createMessage(Message newMessage) {
        Message message = new Message();
        // Set message properties based on the newMessage object
        // For example: message.setText(newMessage.getText());
        return messageRepository.save(message);
    }

    @Override
    public Message getMessageById(Long id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        return optionalMessage.orElse(null);
    }

    @Override
    public boolean deleteMessageById(Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Message updateMessage(Message existingMessage, Message newMessage) {
        // Update the existingMessage properties based on the newMessage object
        // For example: existingMessage.setText(newMessage.getText());
        return messageRepository.save(existingMessage);
    }

    @Override
    public List<Message> getMessagesByRequestId(Long requestId) {
        // Implement the logic to retrieve messages by requestId
        // For example: return messageRepository.findByRequestId(requestId);
        return Collections.emptyList();
    }
}

