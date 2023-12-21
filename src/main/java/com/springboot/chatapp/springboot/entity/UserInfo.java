package com.springboot.chatapp.springboot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean active;
    private String profileStatus;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String profileImageName;
    private String profileImageUrl;
}
