package io.maejeomgo.shlong_mvn;


import io.maejeomgo.shlong_mvn.amenities.Amenity;
import io.maejeomgo.shlong_mvn.user.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class ShlongMvnApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShlongMvnApplication.class, args);
    }


    @Bean
    CommandLineRunner ingestTermOfServiceToVectorStore(
            EmbeddingModel embeddingModel, VectorStore vectorStore,
            @Value("classpath:rag/terms-of-service.txt") Resource termsOfServiceDocs) {

        List<Document> documents = initAmenities().stream().map(
                it -> new Document("amenity: " + it.name() + " " + it.description(), Map.of("description", it.description(), "hours", it.hours(), "location", it.location(), "meta", "amenity"))
        ).toList();

        List<Document> userDocuments = UserData.initUsers().stream().map(
                it -> new Document("guest: " + it.name() + " " + it.age() + " hosting?:" + it.hosting() + " " + it.hobbies() + " " + it.characteristics(), Map.of("meta", "user"))
        ).toList();

        return args -> {
            vectorStore.add(documents);
            vectorStore.similaritySearch("coworking place").forEach(doc -> {
                log.info("Similar Document: {}", doc.getContent());
            });

            vectorStore.add(userDocuments);
            vectorStore.similaritySearch("extrovert person").forEach(doc -> {
                log.info("Similar Document: {}", doc.getContent());
            });

//			// Ingest the document into the vector store
//			vectorStore.write(
//					new TokenTextSplitter().transform(
//							new TextReader(termsOfServiceDocs).read()));
//
//			vectorStore.similaritySearch("Cancelling Bookings").forEach(doc -> {
//				log.info("Similar Document: {}", doc.getContent());
//			});
        };
    }

    private List<Amenity> initAmenities() {
        return List.of(
                new Amenity(
                        "SAY HI",
                        "Your lyf journey begins here! Say hi to our lyf guard and grab a cuppa and some local bites while you check-in with our mobile app.",
                        "24 Hours",
                        "Level 4"
                ),
                new Amenity(
                        "CONNECT - COWORKING LOUNGE",
                        "Get comfy in the communal lounge: work if you must, but if it's a break you're after, there are indulgent couches to chill in and open spaces to work from.",
                        "24 Hours",
                        "Level 4"
                ),
                new Amenity(
                        "WASH AND HANG - LAUNDERETTE",
                        "Dreary chores are a thing of the past. Load your laundry, then read, chat or chill with a beer while your clothes get cleaned.",
                        "24 Hours",
                        "Level 4"
                ),
                new Amenity(
                        "BOND - SOCIAL KITCHEN",
                        "Nothing brings people together like good food in the social kitchen – whip up culinary storms or pick up new recipes from like-minded travellers from around the globe.",
                        "24 Hours",
                        "Level 5"
                ),
                new Amenity(
                        "BURN - SOCIAL GYM",
                        "Work up a sweat in lyf's life-sized giant hamster wheel that functions as a quirky treadmill, or train up your core with our TRX bands. Gym-ing has never been so fun!",
                        "24 Hours",
                        "Level 6"
                )
        );
    }

}
