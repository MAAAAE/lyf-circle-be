package io.maejeomgo.shlong_mvn.event;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class EventDto {
    private String id;
    private String name;
    private EventDate date;
    private List<String> participants;
    private String emoji;
    private String location;
    private String description;
    private String icebreaker;
    private boolean hasNewMessages;
    private LocalDateTime createdAt;
}
