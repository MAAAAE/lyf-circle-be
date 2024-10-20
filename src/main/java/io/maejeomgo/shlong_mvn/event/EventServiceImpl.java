package io.maejeomgo.shlong_mvn.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;


    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
