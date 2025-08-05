package com.emirkoral.deliveryapp.chatmessage;

import com.emirkoral.deliveryapp.chatmessage.dto.ChatMessageRequest;
import com.emirkoral.deliveryapp.chatmessage.dto.ChatMessageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    ChatMessage toEntity(ChatMessageRequest request);
    ChatMessageResponse toResponse(ChatMessage chatMessage);
} 