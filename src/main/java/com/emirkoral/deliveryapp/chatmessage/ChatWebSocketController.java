package com.emirkoral.deliveryapp.chatmessage;

import com.emirkoral.deliveryapp.chatmessage.dto.ChatMessageRequest;
import com.emirkoral.deliveryapp.chatmessage.dto.ChatMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("/chat/order/{orderId}")
    @SendTo("/topic/chat/order/{orderId}")
    public ChatMessageResponse sendMessage(@Payload ChatMessageRequest chatMessageRequest) {
        // Save to DB and broadcast
        return chatMessageService.saveMessage(chatMessageRequest);
    }
} 