package com.springboot.chatapp.springboot.controller;

import com.springboot.chatapp.springboot.entity.Message;
import com.springboot.chatapp.springboot.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    public ChatController(
            SimpMessagingTemplate simpMessagingTemplate,
            MessageService messageService
    ) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/message")
    public void message(@Payload Message message) {
        System.out.println("Message Received : " + message);
        Message messageSaved = messageService.saveMessage(message);

        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(message.getReceiverId()),
                "/queue/messages",
                messageSaved);
    }
}
