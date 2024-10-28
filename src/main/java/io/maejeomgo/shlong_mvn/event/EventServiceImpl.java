package io.maejeomgo.shlong_mvn.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maejeomgo.shlong_mvn.amenities.Amenity;
import io.maejeomgo.shlong_mvn.amenities.AmenityRepository;
import io.maejeomgo.shlong_mvn.vector.VectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final AmenityRepository amenityRepository;
    private final VectorService vectorService;
    private final VectorStore vectorStore;
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;


    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }


    @Override
    public List<Event> makeEvents() {
        List<Amenity> amenities = amenityRepository.findAll();
        List<Amenity> mockAmens = amenities.subList(1, amenities.size());
        List<Event> created = new ArrayList<>();

        for (Amenity amenity : mockAmens) {
            log.info(amenity.getTitle());
//            List<Document> users = vectorService.getUsersByQuery(amenity.getTitle());

            log.info("====");
//            users.forEach(v -> log.info(String.valueOf(v)));
            log.info("====");

            created.add(createEvent(amenity.getTitle()));
            break;

        }

        return created;
    }

    @Override
    public Event makeEvent(int amenityId) {
        Amenity amenity = amenityRepository.findById(amenityId)
                .orElseThrow(() -> new RuntimeException("amenity not found"));
        return createEvent(amenity.getTitle());
    }


    private Event createEvent(String amenity) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();

        String content = chatClient.prompt()
                .system("""
                        You are an hotel amenity event creator.\s
                        You will receive input data containing name of a specified amenity.\s
                        Your task is to generate an social activity that is suitable for the specified amenity. and can easily hang out users that I give to you based on their info. \s
                        value is all english. date will be today.\s
                        listeners are people who wants to join your event.\s
                        your response must starts with '{' for parsing json in my system.\s
                        The response must be structured as follows:
                        {
                              "name": "event name",
                              "date": { "month": "integer", "day": "integer", "weekday": "string", "time": "string" },
                              "participants": the number of users,
                              "emoji": "",
                              "location": "amenity name",
                              "description": "",
                              "icebreaker" : "icebreaker: "" and how to do:",
                              "hasNewMessages": true or false,
                              "users": list of user's nickname that I give to you
                            }
                       \s""")

                .user("Create an creative social activity that the given users can commonly enjoy at amenity:" + amenity + " without event host. "
                + " Please include ice-breaking activities that can be done in this amenity in the icebreaker. Since there is no host, no additional materials can be prepared"
                )
//                .user("Given the specified amenity and users with similar preferences, generate an creative event that participants can mutually enjoy" + amenity + " without event host."
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()
                        .withQuery(amenity)
                        .withTopK(10)
                        .withSimilarityThreshold(0.5)
                        .withFilterExpression(b.eq("type", "user").build())))
                .call()
                .content();


        Event entity = saveJsonToMongo(content);
        if (Objects.nonNull(entity)) {
            entity.setCreatedAt(LocalDateTime.now());
            return eventRepository.save(entity);
        }

        return null;
    }

    public Event saveJsonToMongo(String jsonString) {
        try {
            // Convert JSON string to POJO
            log.info(jsonString);
            return objectMapper.readValue(jsonString, Event.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
