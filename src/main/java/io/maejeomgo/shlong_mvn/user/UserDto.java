package io.maejeomgo.shlong_mvn.user;

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

