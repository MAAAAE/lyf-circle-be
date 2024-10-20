package io.maejeomgo.shlong_mvn.event;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();

    List<Event> makeEvents();
}
