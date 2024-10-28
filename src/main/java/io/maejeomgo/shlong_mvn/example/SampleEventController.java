package io.maejeomgo.shlong_mvn.example;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
class SampleEventController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @GetMapping("/sample/event/create")
    String generateEvent(@RequestParam(value = "prompt", defaultValue = "make a 3 events.") String userInput) {
        return this.chatClient.prompt()
                .system("""
                        you have to give response with json type. be like place, time, people's name list and reason why you choose these members shortly. place should be a existed amenity in lyf funan hotel.
                        Make 3 events suitable for the purpose of the shared facilities that the hotel has. Group users who have similar interests and assign fewer than 5 people to the event.
                        """)
            .user(userInput)
            .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
            .call()
            .content();
    }
}
