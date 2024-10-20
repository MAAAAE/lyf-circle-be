package io.maejeomgo.shlong_mvn.user;

import java.util.List;

public record User(Long id, int age, boolean hosting, String name, List<String> hobbies,
                   List<String> characteristics) {
}
