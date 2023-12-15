package com.springboot.chatapp.springboot.service;

import com.springboot.chatapp.springboot.entity.Message;
import com.springboot.chatapp.springboot.model.PrivateChatResponse;

public interface MessageService {
    Message saveMessage(
            Message message
    );

    PrivateChatResponse getPrivateChatMessages(
            int pageNo,
            int pageSize,
            int senderId,
            int receiverId
    );
}
