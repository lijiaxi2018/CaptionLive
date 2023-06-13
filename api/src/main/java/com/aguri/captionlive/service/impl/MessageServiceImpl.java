package com.aguri.captionlive.service.impl;
import com.aguri.captionlive.model.Message;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.repository.MessageRepository;
import com.aguri.captionlive.service.MessageService;

import java.util.List;

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
        return messageRepository.save(newMessage);
    }

    @Override
    public Message getMessageById(Long id) {

        return messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with id: " + id));
    }

    @Override
    public void deleteMessageById(Long id) {
            messageRepository.deleteById(id);
    }

    @Override
    public Message updateMessage(Long id, Message newMessage) {
        Message existingMessage = getMessageById(id);
        //Need to confirm what attributes should be update
        existingMessage.setContent(newMessage.getContent());
        return messageRepository.save(existingMessage);
    }

    @Override
    public List<Message> getMessagesByRequestId(Long requestId) {
        return messageRepository.findAllByRequestRequestId(requestId);
    }
}

