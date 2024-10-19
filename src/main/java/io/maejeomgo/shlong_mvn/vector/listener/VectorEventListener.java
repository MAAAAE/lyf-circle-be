package io.maejeomgo.shlong_mvn.vector.listener;

import io.maejeomgo.shlong_mvn.CreateUserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

import static java.util.Objects.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class VectorEventListener {

    private final VectorStore vectorStore;

    @EventListener
    void handleCreateUserEvent(CreateUserEvent event) {
        requireNonNull(event);
        log.info("[listener] user created. add user to vector store. {}", event.users().getUsername());

        vectorStore.add(List.of(event.users().createContentForVector()));
    }
}
