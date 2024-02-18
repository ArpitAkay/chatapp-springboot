package com.springboot.chatapp.springboot.controller;

import com.springboot.chatapp.springboot.entity.Message;
import com.springboot.chatapp.springboot.exception.RESTException;
import com.springboot.chatapp.springboot.model.PrivateChatResponse;
import com.springboot.chatapp.springboot.service.MessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(
            MessageService messageService
    ) {
        this.messageService = messageService;
    }

    @GetMapping("/one-to-one")
    public PrivateChatResponse getPrivateChatMessages(
            @RequestParam("pageNo") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "50", required = false) int pageSize,
            @RequestParam("senderId") int senderId,
            @RequestParam("receiverId") int receiverId
    ) {
        return messageService.getPrivateChatMessages(pageNo, pageSize, senderId, receiverId);
    }

    @PutMapping("/update")
    public Message updateMessage(
            @RequestParam("id") int id,
            @RequestParam("content") String content,
            @RequestParam("editedTimestamp") String editedTimestamp
    ) throws RESTException {
        return messageService.updateMessage(id, content, editedTimestamp);
    }


    @DeleteMapping("/delete")
    public String deleteMessage(
            @RequestParam("id") int id
    ) throws RESTException {
        return messageService.deleteMessage(id);
    }

}
