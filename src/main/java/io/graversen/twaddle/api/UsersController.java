package io.graversen.twaddle.api;

import io.graversen.twaddle.data.document.Twaddle;
import io.graversen.twaddle.data.entity.User;
import io.graversen.twaddle.data.model.TwaddleModel;
import io.graversen.twaddle.data.model.TwaddlesModel;
import io.graversen.twaddle.data.repository.elastic.ITwaddleRepository;
import io.graversen.twaddle.data.repository.jpa.IUserRepository;
import io.graversen.twaddle.lib.Utils;
import io.graversen.twaddle.service.TwaddlesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.function.Function;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UsersController
{
    private final IUserRepository userRepository;
    private final ITwaddleRepository twaddleRepository;
    private final TwaddlesService twaddlesService;

    @Value("${twaddle.viewSize}")
    private int viewSize;

    @PostMapping
    public ResponseEntity<Void> createTwaddle()
    {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{userId}/twaddles")
    public ResponseEntity<TwaddlesModel> getTwaddle(@PathVariable String userId)
    {
        final User user = userRepository.findByUserId(userId).orElseThrow();
        final Page<Twaddle> twaddles = twaddleRepository.findByUserIdOrderByCreatedAtDesc(user.getUserId(), defaultTwaddlesPage());

        return ResponseEntity.ok(
                new TwaddlesModel(twaddles.map(Utils.mapTwaddle()).getContent())
        );
    }

    @GetMapping(value = "{userId}/twaddles/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
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

    private PageRequest defaultTwaddlesPage()
    {
        return PageRequest.of(0, viewSize);
    }
}