package io.maejeomgo.shlong_mvn.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceImplTest {


    @Autowired
    private EventService eventService;

    @Test
    void contextLoads() throws Exception {
        assertThat(eventService).isNotNull();
    }

    @Test
    void getAllEvents() {
    }

    @Test
    void makeEvents() {

        assertDoesNotThrow(() -> eventService.makeEvents());

    }
    @Test
    void makeEventsForLoop() {

        assertDoesNotThrow(() -> {
            IntStream.of(1,2,3,4,5).forEach(
                    i -> eventService.makeEvent(i)
            );
        });

    }
}
