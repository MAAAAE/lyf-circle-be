package io.maejeomgo.shlong_mvn.vector;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VectorSearchServiceImpl implements VectorService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    @Override
    public List<Document> getUsersByQuery(@NotEmpty String query) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();

        return vectorStore.similaritySearch(
                SearchRequest.defaults()
                        .withQuery(query)
                        .withTopK(5)
                        .withSimilarityThreshold(0.5)
                        .withFilterExpression(b.eq("type", "user").build())
        );
    }

    @Override
    public List<Document> getAmenityByQuery(@NotEmpty String query) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();

        return vectorStore.similaritySearch(
                SearchRequest.defaults()
                        .withQuery(query)
                        .withTopK(1)
                        .withSimilarityThreshold(0.5)
                        .withFilterExpression(b.eq("type", "amenity").build())
        );
    }
}
