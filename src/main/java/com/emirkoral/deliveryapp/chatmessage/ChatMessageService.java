package com.emirkoral.deliveryapp.chatmessage;

import com.emirkoral.deliveryapp.chatmessage.dto.ChatMessageRequest;
import com.emirkoral.deliveryapp.chatmessage.dto.ChatMessageResponse;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    public ChatMessageService(ChatMessageRepository chatMessageRepository, ChatMessageMapper chatMessageMapper) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatMessageMapper = chatMessageMapper;
    }

    public ChatMessageResponse saveMessage(ChatMessageRequest request) {
        ChatMessage chatMessage = chatMessageMapper.toEntity(request);
        chatMessage.setTimestamp(LocalDateTime.now());
        ChatMessage saved = chatMessageRepository.save(chatMessage);
        return chatMessageMapper.toResponse(saved);
    }

    public List<ChatMessageResponse> getMessagesByOrder(Long orderId) {
        return chatMessageRepository.findByOrderIdOrderByTimestampAsc(orderId)
                .stream()
                .map(chatMessageMapper::toResponse)
                .toList();
    }
} 