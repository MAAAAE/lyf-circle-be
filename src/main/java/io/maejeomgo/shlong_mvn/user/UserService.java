package io.maejeomgo.shlong_mvn.user;

public interface UserService {
    Users createUser(CreateUserRequest createUserRequest);

    Users getUserOrThrow(String id);

    String findUserNickNameById(String id);
}
