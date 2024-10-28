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
    private List<Detail> details;
    private List<String> users;
    @Setter
    private LocalDateTime createdAt;
}
