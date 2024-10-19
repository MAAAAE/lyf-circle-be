package io.maejeomgo.shlong_mvn.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateUserRequest(
        @NotEmpty String username,
        @NotEmpty String password,
        @NotEmpty String nickname,
        @NotEmpty List<String> langs,
        String country,
        @Size(min = 1, message = "At least one language is required")
        @Valid
        List<@NotEmpty(message = "Hobby cannot be empty") String> hobbies,
        @Size(min = 1, message = "At least one language is required")
        @Valid
        List<@NotEmpty(message = "Hobby cannot be empty") String> characteristics
) {
    public Users toDocument() {
        return Users.builder()
                .nickname(nickname)
                .password(password)
                .username(username)
                .langs(langs)
                .country(country)
                .hobbies(hobbies)
                .characteristics(characteristics)
                .build();
    }
}
