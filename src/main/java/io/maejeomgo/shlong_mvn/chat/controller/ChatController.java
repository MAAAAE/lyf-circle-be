package io.maejeomgo.shlong_mvn.chat.controller;

import io.maejeomgo.shlong_mvn.chat.config.StompPrincipal;
import io.maejeomgo.shlong_mvn.chat.constant.ChatType;
import io.maejeomgo.shlong_mvn.chat.controller.dto.ChatMessageRequest;
import io.maejeomgo.shlong_mvn.chat.service.ChatService;
import io.maejeomgo.shlong_mvn.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

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
        String userId = chatMessageRequest.getSenderId();
        headerAccessor.setUser(new StompPrincipal(userId));

        if (chatService.isFirstTimeEntering(eventId, userId)) {
            chatMessageRequest.setType(ChatType.CHAT);
            // TODO: User가 진입했을 때, AI가 채팅을 보내준다.
            chatMessageRequest.setContent(aiResponse(userService.findUserNickNameById(userId)));
            chatMessageRequest.setEventId(eventId);
            chatService.sendAndSaveMessage(chatMessageRequest);
            return;
        }
        chatService.sendChatHistoryToUser(eventId, userId);
    }

    private String aiResponse(String user) {
        return String.format("[%s] has joined us—let's chat!!!", user);
    }
}

