package com.aguri.captionlive.service;
import com.aguri.captionlive.DTO.MessageRequest;
import com.aguri.captionlive.model.Message;
import java.util.List;

public interface MessageService {

    List<Message> getAllMessages();

    Message createMessage(MessageRequest newMessage);

    Message getMessageById(Long id);

    void deleteMessageById(Long id);

    Message updateMessage(Long id, MessageRequest newMessage);

    List<Message> getMessagesByRequestId(Long requestId);
}

