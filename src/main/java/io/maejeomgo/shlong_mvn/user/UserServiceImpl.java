package io.maejeomgo.shlong_mvn.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Users createUser(final CreateUserRequest createUserRequest) {
        return userRepository.save(createUserRequest.toDocument());
    }
}
