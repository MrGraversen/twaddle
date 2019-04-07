package io.graversen.twaddle.service;

import io.graversen.twaddle.data.entity.User;
import io.graversen.twaddle.data.repository.jpa.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final IUserRepository userRepository;

    private List<User> users;
    private Map<String, User> userCache;

    public List<User> allUsers()
    {
        if (users == null || users.isEmpty())
        {
            users = Collections.unmodifiableList(userRepository.findAll());
            userCache = users.stream().collect(Collectors.toUnmodifiableMap(User::getUserId, user -> user));
        }

        return users;
    }

    public Optional<User> getUser(String userId)
    {
        Objects.requireNonNullElseGet(userId, Optional::empty);
        return Optional.ofNullable(userCache.get(userId));
    }
}
