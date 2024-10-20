package io.maejeomgo.shlong_mvn.chat.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByEventIdOrderByTimestampAsc(String eventId);
}
