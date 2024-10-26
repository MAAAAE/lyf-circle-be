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
                        You are an AI designed to create event details in given JSON format.\s
                        You will receive input data containing name of a specified amenity and a list of users who have similar preferences.\s
                        Your task is to generate an event that is suitable for the specified amenity. and can easily hang out users that I give to you. \s
                        value is all english. date will be today.\s
                        your response must start with '{' for parsing json in my system.
                       \s
                         The response must be structured as follows:
                        {
                              "name": "event name",
                              "date": { "month": 5, "day": 20, "weekday": "토", "time": "15:00" },
                              "participants": the number of users,
                              "emoji": "",
                              "location": "amenity name",
                              "description": "",
                              "hasNewMessages": true or false,
                              "details": [
                                { "title": "prepare", "content": "편한 복장, 물, 요가 매트 (대여 가능)" },
                                { "title": "소요 시간", "content": "약 1시간 30분" },
                                { "title": "난이도", "content": "초급 ~ 중급" },
                                { "title": "인원 제한", "content": "최대 10명" }
                              ],
                              "users": list of user's nickname that I give to you
                            }
                       \s""")

//                .user("Create an event that the given users can commonly enjoy at amenity:" + amenity + " without event host: ")
                .user("Given the specified amenity and users with similar preferences, generate an creative event that participants can mutually enjoy" + amenity + " without event host")
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()
                        .withQuery(amenity)
                        .withTopK(5)
                        .withSimilarityThreshold(0.5)
                        .withFilterExpression(b.eq("type", "user").build())))
                .call()
                .content();


        Event entity = saveJsonToMongo(content);
        if (Objects.nonNull(entity)) {
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
