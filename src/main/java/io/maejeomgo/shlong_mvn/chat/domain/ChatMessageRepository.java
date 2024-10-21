package io.maejeomgo.shlong_mvn.chat.domain;

import io.maejeomgo.shlong_mvn.chat.constant.ChatType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByEventIdAndTypeOrderByTimestampAsc(String eventId, ChatType type);
    Boolean existsChatMessageByEventIdAndSender(String eventId, String sender);
}
