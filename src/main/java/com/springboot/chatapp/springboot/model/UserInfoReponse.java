package com.springboot.chatapp.springboot.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoReponse {
    private int id;
    private String name;
    private String profileImageUrl;
    private String latestMessage;
    private String latestMessageTime;
}
