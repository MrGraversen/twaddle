package io.graversen.twaddle.service;

import io.graversen.twaddle.data.document.Twaddle;
import io.graversen.twaddle.data.repository.elastic.ITwaddleRepository;
import io.graversen.twaddle.data.repository.jpa.IUserRepository;
import io.graversen.twaddle.lib.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TwaddlesService
{
    private final IUserRepository userRepository;
    private final ITwaddleRepository twaddleRepository;
    private final List<String> adjectives;
    private final List<String> animals;

    private final Random random = new Random();

    @Scheduled(fixedRate = 2000L, initialDelay = 2000L)
    public void generateTwaddle()
    {
        userRepository.findAll().forEach(user ->
        {
            if (random.nextBoolean())
            {
                twaddleRepository.save(new Twaddle(user.getUserId(), Utils.randomTwaddle(animals, adjectives)));
            }
        });
    }
}
