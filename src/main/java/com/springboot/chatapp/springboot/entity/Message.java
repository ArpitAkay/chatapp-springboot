package com.springboot.chatapp.springboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    private int senderId;
    private String senderName;
    private int receiverId;
    private String receiverName;
    private String timestamp;
    private String editedTimestamp;
}
