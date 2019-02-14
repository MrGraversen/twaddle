package io.graversen.twaddle.api;

import io.graversen.twaddle.data.entity.User;
import io.graversen.twaddle.data.repository.jpa.IUserRepository;
import io.graversen.twaddle.service.TwaddlesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UsersController
{
    private final IUserRepository userRepository;
    private final TwaddlesService twaddlesService;

    @PostMapping
    public ResponseEntity<Void> createTwaddle()
    {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{userId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getTwaddleStream(@PathVariable String userId)
    {
        final User user = userRepository.findByUserId(userId).orElseThrow();
        return eventEmitter(user);
    }

    private SseEmitter eventEmitter(User user)
    {
        final SseEmitter sseEmitter = new SseEmitter();

        twaddlesService.subscribe(user, sseEmitter);
        sseEmitter.onCompletion(() -> twaddlesService.unsubscribe(user, sseEmitter));
        sseEmitter.onTimeout(() -> twaddlesService.unsubscribe(user, sseEmitter));
        sseEmitter.onError(e -> twaddlesService.unsubscribe(user, sseEmitter));

        return sseEmitter;
    }
}