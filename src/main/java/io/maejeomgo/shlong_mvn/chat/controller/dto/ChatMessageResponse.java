package io.maejeomgo.shlong_mvn.chat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ChatMessageResponse {
    private String type;
    private String content;
    private String sender;
    private String eventId;
    private LocalDateTime timestamp;
}
