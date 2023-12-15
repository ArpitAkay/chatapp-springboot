package com.springboot.chatapp.springboot.model;

import com.springboot.chatapp.springboot.entity.Message;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrivateChatResponse {
    private List<Message> messages;
    private boolean isLastPage;
    private boolean isFirstPage;
    private int totalPages;
    private int pageNo;
    private int pageSize;
}
