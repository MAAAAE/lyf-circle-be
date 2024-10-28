package io.maejeomgo.shlong_mvn.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;


    @PostMapping("/api/user")
    Users createUser(@RequestBody @Validated final CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }
}
