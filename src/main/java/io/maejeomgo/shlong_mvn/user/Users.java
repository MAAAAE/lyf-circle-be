package io.maejeomgo.shlong_mvn.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Builder
@Getter
@Document(collection = "users")
public class Users {

    @Id
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "langs", nullable = false)
    private List<String> langs;

    @Column(nullable = false)
    private String country;

    private List<String> hobbies;

    private List<String> characteristics;


    public org.springframework.ai.document.Document createContentForVector() {
        // 각 필드를 문장으로 변환

        String content = String.format("Username: %s. Nickname: %s. Speaks languages: %s. Lives in: %s. Hobbies include: %s. Characteristics are: %s.",
                this.getUsername(),
                this.getNickname(),
                String.join(", ", this.getLangs()),
                this.getCountry(),
                this.getHobbies() != null ? String.join(", ", this.getHobbies()) : "None",
                this.getCharacteristics() != null ? String.join(", ", this.getCharacteristics()) : "None");

        return new org.springframework.ai.document.Document(content, toMetaMap());

    }

    public Map<String, Object> toMetaMap() {
        return Map.of(
                "type", "user",
                "id", id,
                "username", username,
                "password", password,
                "nickname", nickname,
                "langs", langs,
                "country", country,
                "hobbies", hobbies,
                "characteristics", characteristics
        );
    }

}

