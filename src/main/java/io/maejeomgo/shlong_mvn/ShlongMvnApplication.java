package io.maejeomgo.shlong_mvn;


import io.maejeomgo.shlong_mvn.amenities.AmenityDto;
import io.maejeomgo.shlong_mvn.user.MockUserGenerator;
import io.maejeomgo.shlong_mvn.user.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class ShlongMvnApplication {

    private final boolean clearDataOnStartup;

    public ShlongMvnApplication(@Value("${mongodb.clear-data-on-startup:false}") boolean clearDataOnStartup) {
        this.clearDataOnStartup = clearDataOnStartup;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShlongMvnApplication.class, args);
    }


    @Bean
    CommandLineRunner ingestTermOfServiceToVectorStore(
            MongoTemplate mongoTemplate,
            VectorStore vectorStore,
            @Value("classpath:rag/terms-of-service.txt") Resource termsOfServiceDocs) {

        List<Document> documents = initAmenities().stream().map(
                it -> new Document("amenity: " + it.name() + " " + it.description(), Map.of("description", it.description(), "hours", it.hours(), "location", it.location(), "type", "amenity"))
        ).toList();

        List<Document> userDocuments = MockUserGenerator.initUsers().stream().map(Users::createContentForVector)
                .toList();

        return args -> {
            clearMongoDb(mongoTemplate);

            vectorStore.add(documents);
            vectorStore.add(userDocuments);

//            vectorStore.similaritySearch("coworking place").forEach(doc -> {
//                log.info("Similar Document: {}", doc.getContent());
//            });
//            vectorStore.similaritySearch("extrovert person").forEach(doc -> {
//            vectorStore.add(userDocuments);
//                log.info("Similar Document: {}", doc.getContent());
//            });

//			// Ingest the document into the vector store
//			vectorStore.write(
//					new TokenTextSplitter().transform(
//							new TextReader(termsOfServiceDocs).read()));
        };
    }

    private void clearMongoDb(MongoTemplate mongoTemplate) {
        if (clearDataOnStartup) {
            // 모든 컬렉션 삭제
            mongoTemplate.getDb().drop();
            System.out.println("All MongoDB collections have been dropped.");
        } else {
            System.out.println("MongoDB data deletion is disabled.");
        }
    }

    private List<AmenityDto> initAmenities() {
        return List.of(
                new AmenityDto(
                        "SAY HI",
                        "Your lyf journey begins here! Say hi to our lyf guard and grab a cuppa and some local bites while you check-in with our mobile app.",
                        "24 Hours",
                        "Level 4"
                ),
                new AmenityDto(
                        "CONNECT - COWORKING LOUNGE",
                        "Get comfy in the communal lounge: work if you must, but if it's a break you're after, there are indulgent couches to chill in and open spaces to work from.",
                        "24 Hours",
                        "Level 4"
                ),
                new AmenityDto(
                        "WASH AND HANG - LAUNDERETTE",
                        "Dreary chores are a thing of the past. Load your laundry, then read, chat or chill with a beer while your clothes get cleaned.",
                        "24 Hours",
                        "Level 4"
                ),
                new AmenityDto(
                        "BOND - SOCIAL KITCHEN",
                        "Nothing brings people together like good food in the social kitchen – whip up culinary storms or pick up new recipes from like-minded travellers from around the globe.",
                        "24 Hours",
                        "Level 5"
                ),
                new AmenityDto(
                        "BURN - SOCIAL GYM",
                        "Work up a sweat in lyf's life-sized giant hamster wheel that functions as a quirky treadmill, or train up your core with our TRX bands. Gym-ing has never been so fun!",
                        "24 Hours",
                        "Level 6"
                )
        );
    }

}
