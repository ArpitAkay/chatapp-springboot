package com.springboot.chatapp.springboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConversationThreads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String latestMessage;
    private String lastMessageTime;
    private int senderId;
    private String senderName;
    private int receiverId;
    private String receiverName;
}
