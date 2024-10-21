package io.maejeomgo.shlong_mvn.chat.service;

import io.maejeomgo.shlong_mvn.chat.constant.ChatType;
import io.maejeomgo.shlong_mvn.chat.controller.dto.ChatMessageRequest;
import io.maejeomgo.shlong_mvn.chat.controller.dto.ChatMessageResponse;
import io.maejeomgo.shlong_mvn.chat.domain.ChatMessage;
import io.maejeomgo.shlong_mvn.chat.domain.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private static final String TOPIC = "/topic/";
    private static final String QUEUE_HISTORY = "/queue/history/";

    @Transactional
    public void sendAndSaveMessage(ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = ChatMessage.builder()
                .type(chatMessageRequest.getType())
                .content(chatMessageRequest.getContent())
                .sender(chatMessageRequest.getSender())
                .eventId(chatMessageRequest.getEventId())
                .timestamp(LocalDateTime.now())
                .build();

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        ChatMessageResponse response = convertToResponse(savedMessage);
        messagingTemplate.convertAndSend(TOPIC + response.getEventId(), response);
    }

    public List<ChatMessageResponse> getChatHistory(String eventId) {
        return chatMessageRepository.findByEventIdAndTypeOrderByTimestampAsc(eventId, ChatType.CHAT)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean isFirstTimeEntering(String eventId, String sender) {
        return !chatMessageRepository.existsChatMessageByEventIdAndSender(eventId, sender);
    }

    public void sendChatHistoryToUser(String eventId, String sender) {
        List<ChatMessageResponse> chatHistory = getChatHistory(eventId);
        messagingTemplate.convertAndSend(QUEUE_HISTORY + eventId, chatHistory);
    }

    private ChatMessageResponse convertToResponse(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .type(chatMessage.getType().name())
                .content(chatMessage.getContent())
                .eventId(chatMessage.getEventId())
                .sender(chatMessage.getSender())
                .timestamp(chatMessage.getTimestamp())
                .build();
    }
}