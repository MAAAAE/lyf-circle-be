package io.maejeomgo.shlong_mvn.event;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
class EventController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final EventService eventService;
    @GetMapping("/event/create")
    String generateEvent(@RequestParam(value = "prompt", defaultValue = "make a 3 events.") String userInput) {
        return this.chatClient.prompt()
                .system("""
                        you have to give response with json type. be like place, time, people's name list and reason why you choose these members shortly. place should be a existed amenity in lyf funan hotel.
                        Make 3 events suitable for the purpose of the shared facilities that the hotel has. Group users who have similar interests and assign fewer than 5 people to the event.
                        you also have to think about user's characteristcs.
                        """)
//                .system("""
//                        You will be responsible for creating a program for people staying at a hostel.
//                        Based on the given user information and amenities, recommend indoor hobby activities that would suit each person. If there are no suitable activities currently available in the hostel for the person, create and schedule an appropriate activity. If there are people who have similar hobbies, make a group for them. If you can’t recommand the activities, Just return empty output.
//                        """)
            .user(userInput)
            .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
            .call()
            .content();
    }

    @GetMapping("/event")
    List<EventDto> getAllEvents() {
        log.info("get all events");

        return eventService.getAllEvents().stream().map(Event::to).toList();
    }
    @PostMapping("/event/all")
    List<Event> makeNewEvents() {
        log.info("[openAI] make new events..");
        return eventService.makeEvents();
    }
    @PostMapping("/event/{amenityId}")
    Event makeNewEvent(@PathVariable @PositiveOrZero int amenityId) {
        log.info("[lyf circle API] event creation request received! amenity: {}", amenityId);

        return eventService.makeEvent(amenityId);
    }
}
