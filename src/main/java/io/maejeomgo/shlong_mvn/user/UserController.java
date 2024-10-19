package io.maejeomgo.shlong_mvn.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;


    @PostMapping("/user")
    Users createUser(@RequestBody @Valid final CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }
}
