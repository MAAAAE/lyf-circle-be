package io.maejeomgo.shlong_mvn.event;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
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
    private boolean hasNewMessages;
    private List<Detail> details;
}
