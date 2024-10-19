package io.maejeomgo.shlong_mvn.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockUserGeneratorTest {

    @Test
    void generate() {
        MockUserGenerator.initUsers().stream().map(Users::createContentForVector)
                .forEach(System.out::println);
    }
}
