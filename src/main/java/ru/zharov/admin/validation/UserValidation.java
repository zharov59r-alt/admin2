package ru.zharov.admin.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.zharov.admin.entity.User;
import ru.zharov.admin.exception.ValidationException;
import ru.zharov.admin.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    public void check(User user) {
        Optional<Long> u = userRepository.findByEmail(user.getEmail());
        Long userId = user.getId();
        if (u.isPresent()) {
            if (userId == null || userId != null && userId != u.get())
                throw new ValidationException("Этот email уже используется");
        }

    }

}
