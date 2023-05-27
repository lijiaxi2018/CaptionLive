package com.aguri.captionlive.service;
import com.aguri.captionlive.model.Message;
import java.util.List;

public interface MessageService {

    List<Message> getAllMessages();

    Message createMessage(Message newMessage);

    Message getMessageById(Long id);

    boolean deleteMessageById(Long id);

    Message updateMessage(Message existingMessage, Message newMessage);

    List<Message> getMessagesByRequestId(Long requestId);
}

