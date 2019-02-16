package io.graversen.twaddle.service;

import io.graversen.twaddle.config.TwaddleChannels;
import io.graversen.twaddle.data.document.Twaddle;
import io.graversen.twaddle.data.entity.User;
import io.graversen.twaddle.data.repository.elastic.ITwaddleRepository;
import io.graversen.twaddle.data.repository.jpa.IUserRepository;
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
    private final IUserRepository userRepository;
    private final ITwaddleRepository twaddleRepository;
    private final TwaddleChannels twaddleChannels;
    private final List<String> adjectives;
    private final List<String> animals;
    private final List<String> colors;
    private final List<String> cities;

    private final Random random = new Random();
    private final ConcurrentMap<String, List<SseEmitter>> twaddleSubscriptions = new ConcurrentHashMap<>();

    private List<User> users = null;
    private long usersUpdated = System.currentTimeMillis();
    private long userUpdateInterval = 30000L;

    @Scheduled(fixedRate = 2000L, initialDelay = 2000L)
    public void generateRandomTwaddles()
    {
        if (users == null || (System.currentTimeMillis() > (usersUpdated + userUpdateInterval)))
        {
            users = Collections.synchronizedList(userRepository.findAll());
            usersUpdated = System.currentTimeMillis();
        }

        users.forEach(user ->
        {
            final Twaddle twaddle = new Twaddle(user.getUserId(), Utils.randomTwaddle(animals, adjectives, cities, colors));

            if (Utils.defaultUsers().contains(user.getUserId()))
            {
                twaddleChannels.twaddles().send(MessageBuilder.withPayload(twaddle).build());
                return;
            }

            if (random.nextBoolean())
            {
                twaddleChannels.twaddles().send(MessageBuilder.withPayload(twaddle).build());
            }
        });
    }

    @StreamListener("twaddles")
    public void emitTwaddle(Twaddle twaddle)
    {
        final List<SseEmitter> sseEmitters = twaddleSubscriptions.getOrDefault(twaddle.getUserId(), Collections.emptyList());
        sseEmitters.forEach(doEmitTwaddle(twaddle));
    }

    @StreamListener("twaddles")
    public void saveTwaddle(Twaddle twaddle)
    {
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

    private Consumer<SseEmitter> doEmitTwaddle(Twaddle twaddle)
    {
        return sseEmitter ->
        {
            try
            {
                sseEmitter.send(twaddle);
            }
            catch (IOException e)
            {
                // ¯\_(ツ)_/¯
            }
        };
    }
}
