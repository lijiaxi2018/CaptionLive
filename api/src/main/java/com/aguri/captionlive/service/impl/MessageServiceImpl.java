package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.model.Message;
import com.aguri.captionlive.model.Request;
import com.aguri.captionlive.DTO.MessageRequest;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.repository.MessageRepository;
import com.aguri.captionlive.repository.RequestRepository;
import com.aguri.captionlive.service.MessageService;
import com.aguri.captionlive.service.RequestService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    RequestService requestService;

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message createMessage(MessageRequest newMessage) {
        Boolean isReply = newMessage.getIsReply();
        if (!isReply) {
            // the first message for this request
            List<Message> messagesByRequestId = getMessagesByRequestId(newMessage.getRequestId());
            int size = messagesByRequestId.size();
            if (size != 0) {
                // it already has message existed.
                return messagesByRequestId.get(0);
            }
        }
        Message message = new Message();
        Request request;
        request = requestService.getRequestById(newMessage.getRequestId());
        message.setRequest(request);
        message.setContent(newMessage.getContent());
        message.setIsReply(isReply);

        return messageRepository.save(message);
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
    public Message updateMessage(Long id, MessageRequest newMessage) {
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

