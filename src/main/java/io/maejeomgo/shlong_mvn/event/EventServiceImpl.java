package io.maejeomgo.shlong_mvn.event;

import io.maejeomgo.shlong_mvn.amenities.Amenity;
import io.maejeomgo.shlong_mvn.amenities.AmenityRepository;
import io.maejeomgo.shlong_mvn.vector.VectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final AmenityRepository amenityRepository;
    private final VectorService vectorService;


    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }


    @Override
    public List<Event> makeEvents() {
        List<Amenity> amenities = amenityRepository.findAll();
        List<Amenity> mockAmens = amenities.subList(1, amenities.size());

        mockAmens.forEach(
                amenity -> {
                    log.info(amenity.getTitle());
                    List<Document> users = vectorService.getUsersByQuery(amenity.getTitle());

                    log.info("====");
                    users.forEach(v -> log.info(String.valueOf(v)));
                    log.info("====");
                }
        );

        return Collections.emptyList();
    }

    ;
}
