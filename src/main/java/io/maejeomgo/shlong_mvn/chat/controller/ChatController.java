package io.maejeomgo.shlong_mvn.chat.controller;

import io.maejeomgo.shlong_mvn.chat.config.StompPrincipal;
import io.maejeomgo.shlong_mvn.chat.constant.ChatType;
import io.maejeomgo.shlong_mvn.chat.controller.dto.ChatMessageRequest;
import io.maejeomgo.shlong_mvn.chat.service.ChatService;
import io.maejeomgo.shlong_mvn.user.UserService;
import io.maejeomgo.shlong_mvn.user.Users;
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

    @MessageMapping("/chat.sendMessage/{eventId}")
    public void sendMessage(@DestinationVariable String eventId, @Payload ChatMessageRequest chatMessageRequest) {
        chatMessageRequest.setEventId(eventId);
        chatService.sendAndSaveMessage(chatMessageRequest);
    }

    @MessageMapping("/chat.addUser/{eventId}")
    public void addUser(@DestinationVariable String eventId, @Payload ChatMessageRequest chatMessageRequest, SimpMessageHeaderAccessor headerAccessor) {
        String userId = chatMessageRequest.getSenderId();
        headerAccessor.setUser(new StompPrincipal(userId));

        if (chatService.isFirstTimeEntering(eventId, userId)) {
            // 이게 거기임
            // 이벤트에서. 버그가 있음.
            Users userOrThrow = userService.getUserOrThrow(userId);
            String username = userOrThrow.getUsername();
            chatMessageRequest.setType(ChatType.CHAT);
            chatMessageRequest.setContent(getWelComeMessageFormat(username, userOrThrow));
            chatMessageRequest.setSenderId("ai");
            chatMessageRequest.setEventId(eventId);
            chatService.sendAndSaveMessage(chatMessageRequest);
            return;
        }
        chatService.sendChatHistoryToUser(eventId, userId);
    }

    private static String getWelComeMessageFormat(String username, Users userOrThrow) {
        return String.format("@%s has joined us—let's chat!!!\n @%s likes [%s] and speak %s. please introduce yourself.", username, username, userOrThrow.getHobbies(), userOrThrow.getLangs());
    }
}

