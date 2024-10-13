package io.maejeomgo.shlong_mvn.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

public record User(Long id, int age, boolean hosting, String name, List<String> hobbies,
                   List<String> characteristics) {
}
