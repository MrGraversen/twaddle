package io.graversen.twaddle.service;

import io.graversen.twaddle.config.TwaddleChannels;
import io.graversen.twaddle.data.document.Hashtag;
import io.graversen.twaddle.data.document.Twaddle;
import io.graversen.twaddle.data.entity.User;
import io.graversen.twaddle.data.model.TwaddleModel;
import io.graversen.twaddle.data.repository.elastic.IHashTagRepository;
import io.graversen.twaddle.data.repository.elastic.ITwaddleRepository;
import io.graversen.twaddle.lib.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TwaddlesService
{
    private final UserService userService;
    private final ITwaddleRepository twaddleRepository;
    private final IHashTagRepository hashTagRepository;
    private final TwaddleChannels twaddleChannels;
    private final List<String> adjectives;
    private final List<String> animals;
    private final List<String> colors;
    private final List<String> cities;

    private final Random random = new Random();
    private final ConcurrentMap<String, List<SseEmitter>> twaddleSubscriptions = new ConcurrentHashMap<>();

    private long usersUpdated = System.currentTimeMillis();
    private long userUpdateInterval = 30000L;

    @Scheduled(fixedRate = 2000L, initialDelay = 2000L)
    public void generateRandomTwaddles()
    {
        userService.allUsers().forEach(user ->
        {
            final Twaddle twaddle = Utils.randomTwaddle(user.getUserId(), animals, adjectives, cities, colors);

            if (Utils.defaultUsers().contains(user.getUserId()) || random.nextBoolean())
            {
                twaddleChannels.twaddles().send(MessageBuilder.withPayload(twaddle).build());
            }
        });
    }

    @StreamListener("twaddles")
    public void emitTwaddle(Twaddle twaddle)
    {
        final List<SseEmitter> sseEmitters = twaddleSubscriptions.getOrDefault(twaddle.getUserId(), Collections.emptyList());
        final User user = userService.getUser(twaddle.getUserId()).orElse(new User("nobody", "nobody"));
        sseEmitters.forEach(doEmitTwaddle(twaddle, user));
    }

    @StreamListener("twaddles")
    public void saveTwaddle(Twaddle twaddle)
    {
        twaddle.getHashTags().forEach(s -> hashTagRepository.save(new Hashtag(twaddle.getUserId(), s)));
        twaddleRepository.save(twaddle);
    }

    public void subscribe(User user, SseEmitter sseEmitter)
    {
        final List<SseEmitter> sseEmitters = twaddleSubscriptions.computeIfAbsent(user.getUserId(), s -> new CopyOnWriteArrayList<>());
        sseEmitters.add(sseEmitter);
    }

    public void unsubscribe(User user, SseEmitter sseEmitter)
    {
        final List<SseEmitter> sseEmitters = twaddleSubscriptions.computeIfAbsent(user.getUserId(), s -> new CopyOnWriteArrayList<>());
        sseEmitters.remove(sseEmitter);
    }

    private Consumer<SseEmitter> doEmitTwaddle(Twaddle twaddle, User user)
    {
        return sseEmitter ->
        {
            try
            {
                final TwaddleModel twaddleModel = Utils.mapTwaddle().apply(twaddle);
                twaddleModel.setUsername(user.getUsername());
                sseEmitter.send(twaddleModel);
            }
            catch (IOException e)
            {
                // ¯\_(ツ)_/¯
            }
        };
    }
}
