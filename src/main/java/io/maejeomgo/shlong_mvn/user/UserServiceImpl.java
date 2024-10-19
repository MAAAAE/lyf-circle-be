package io.maejeomgo.shlong_mvn.user;

import io.maejeomgo.shlong_mvn.CreateUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Users createUser(final CreateUserRequest createUserRequest) {
        Users saved = userRepository.save(createUserRequest.toDocument());
        eventPublisher.publishEvent(new CreateUserEvent(saved));

        return saved;
    }
}
