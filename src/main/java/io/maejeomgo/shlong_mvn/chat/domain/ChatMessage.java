package io.maejeomgo.shlong_mvn.chat.domain;

import io.maejeomgo.shlong_mvn.chat.constant.ChatType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
@Document(collection = "chat_messages")
public class ChatMessage {

    @Id
    private String id;

    @Column(nullable = false)
    private ChatType type;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String eventId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public ChatMessage() {
        this.timestamp = LocalDateTime.now();
    }
}
