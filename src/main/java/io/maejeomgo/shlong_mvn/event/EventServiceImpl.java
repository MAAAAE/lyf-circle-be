package io.maejeomgo.shlong_mvn.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.maejeomgo.shlong_mvn.amenities.Amenity;
import io.maejeomgo.shlong_mvn.amenities.AmenityRepository;
import io.maejeomgo.shlong_mvn.vector.VectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

        for (Amenity amenity : mockAmens) {
            log.info(amenity.getTitle());
            List<Document> users = vectorService.getUsersByQuery(amenity.getTitle());

            log.info("====");
            users.forEach(v -> log.info(String.valueOf(v)));
            log.info("====");

            createEvent(amenity.getTitle());
            break;

        }

        return eventRepository.findAll();
    }


    private void createEvent(String amenity) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();

        String content = chatClient.prompt()
                .system("""
                        you have to give response with json type. key is same with below. start with '{' for parsing as a json in my system.
                        value is all english. date will be today.
                        example: 
                        {
                              "name": "도심 속 힐링 요가 클래스",
                              "date": { "month": 5, "day": 20, "weekday": "토", "time": "15:00" },
                              "participants": 3,
                              "emoji": "🧘",
                              "location": "서울숲 공원",
                              "description": "",
                              "hasNewMessages": true,
                              "details": [
                                { "title": "prepare", "content": "편한 복장, 물, 요가 매트 (대여 가능)" },
                                { "title": "소요 시간", "content": "약 1시간 30분" },
                                { "title": "난이도", "content": "초급 ~ 중급" },
                                { "title": "인원 제한", "content": "최대 10명" }
                              ]
                            }
                        """)

                .user("Create an event that the given users can commonly enjoy at amenity:" + amenity + " without event host: ")
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()
                        .withQuery(amenity)
                        .withTopK(5)
                        .withSimilarityThreshold(0.5)
                        .withFilterExpression(b.eq("type", "user").build())))
                .call()
                .content();


        Event entity = saveJsonToMongo(content);
        if (Objects.nonNull(entity)) {
            eventRepository.save(entity);
        }
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
