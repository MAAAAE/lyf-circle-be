package io.maejeomgo.shlong_mvn.vector;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.ai.document.Document;

import java.util.List;

public interface VectorService {
    List<Document> getUsersByQuery(@NotEmpty String query);

    List<Document> getAmenityByQuery(@NotEmpty String query);
}
