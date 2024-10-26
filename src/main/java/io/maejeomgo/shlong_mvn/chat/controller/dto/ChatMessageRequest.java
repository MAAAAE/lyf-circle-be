package io.maejeomgo.shlong_mvn.chat.controller.dto;

import io.maejeomgo.shlong_mvn.chat.constant.ChatType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequest {
    private ChatType type;
    private String content;
    private String senderId;
    private String eventId;
}
