package io.maejeomgo.shlong_mvn.event;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Document(collection = "events")
public class Event {

    @Id
    private String id;
    private String name;
    private EventDate date;
    private int participants;
    private String emoji;
    private String location;
    private String description;
    private String icebreaker;
    private boolean hasNewMessages;
    private List<String> users;
    @Setter
    private LocalDateTime createdAt;

    public EventDto to() {
        return EventDto.builder()
                .id(id)
                .name(name)
                .date(date)
                .participants(users)
                .emoji(emoji)
                .location(location)
                .description(description)
                .icebreaker(icebreaker)
                .hasNewMessages(hasNewMessages)
                .createdAt(createdAt)
                .build();
    }
}
