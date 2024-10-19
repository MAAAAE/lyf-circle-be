package io.maejeomgo.shlong_mvn.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

public record UserDto(String id,
                   String username,
                   String password,
                   String nickname,
                   List<String> langs,
                   String country,
                   List<String> hobbies,
                   List<String> characteristics) {
}

