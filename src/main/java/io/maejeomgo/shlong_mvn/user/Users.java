package io.maejeomgo.shlong_mvn.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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

}

