package com.emirkoral.deliveryapp.chatmessage;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByOrderIdOrderByTimestampAsc(Long orderId);
} 