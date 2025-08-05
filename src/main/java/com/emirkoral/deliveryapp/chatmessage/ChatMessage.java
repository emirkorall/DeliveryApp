package com.emirkoral.deliveryapp.chatmessage;

import com.emirkoral.deliveryapp.user.User.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long senderId;
    @Enumerated(EnumType.STRING)
    private UserRole senderRole;
    private String message;
    private LocalDateTime timestamp;
} 