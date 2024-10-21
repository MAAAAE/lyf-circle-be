package io.maejeomgo.shlong_mvn.chat.controller;

import io.maejeomgo.shlong_mvn.chat.config.StompPrincipal;
import io.maejeomgo.shlong_mvn.chat.constant.ChatType;
import io.maejeomgo.shlong_mvn.chat.controller.dto.ChatMessageRequest;
import io.maejeomgo.shlong_mvn.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    // 클라이언트가 "/app/chat.sendMessage/{eventId}"로 메시지를 보내면 이 메서드가 호출됩니다.
    @MessageMapping("/chat.sendMessage/{eventId}")
    public void sendMessage(@DestinationVariable String eventId, @Payload ChatMessageRequest chatMessageRequest) {
        // 해당 이벤트의 구독자들에게 메시지를 전송합니다.
        chatMessageRequest.setEventId(eventId);
        chatService.sendAndSaveMessage(chatMessageRequest);
    }

    // 클라이언트가 "/app/chat.addUser/{eventId}"로 메시지를 보내면 이 메서드가 호출됩니다.
    @MessageMapping("/chat.addUser/{eventId}")
    public void addUser(@DestinationVariable String eventId, @Payload ChatMessageRequest chatMessageRequest, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.setUser(new StompPrincipal(chatMessageRequest.getSender()));

        if (chatService.isFirstTimeEntering(eventId, chatMessageRequest.getSender())) {
            System.out.println("HELLO!");
            chatMessageRequest.setType(ChatType.JOIN);
            chatMessageRequest.setContent("안녕하세요! 누구누구님이 입장하였습니다.");
            chatMessageRequest.setEventId(eventId);
            // TODO: User가 진입했을 때, AI가 채팅을 보내준다.
            chatService.sendAndSaveMessage(chatMessageRequest);
        }
        System.out.println("History!");
        chatService.sendChatHistoryToUser(eventId, chatMessageRequest.getSender());
    }
}

