package io.graversen.twaddle.config;

import io.graversen.twaddle.data.document.Twaddle;
import io.graversen.twaddle.data.entity.User;
import io.graversen.twaddle.data.repository.elastic.IHashTagRepository;
import io.graversen.twaddle.data.repository.elastic.ITwaddleRepository;
import io.graversen.twaddle.data.repository.jpa.IUserRepository;
import io.graversen.twaddle.lib.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Configuration
@ComponentScan(basePackages = {"io.graversen.twaddle.service", "io.graversen.twaddle.api", "io.graversen.twaddle.web"})
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class ApplicationConfig implements ApplicationListener<ApplicationReadyEvent>
{
    private final static int NUMBER_OF_RANDOM_USERS = 1000;

    private final ITwaddleRepository twaddleRepository;
    private final IHashTagRepository hashTagRepository;
    private final IUserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent)
    {
        twaddleRepository.deleteAll();
        hashTagRepository.deleteAll();
        userRepository.deleteAll();

        final User userMartin = userRepository.save(new User("MARTIN", "MARTIN"));
        userRepository.save(new User("STEFFEN", "STEFFEN"));
        userRepository.save(new User("LASSE", "LASSE"));

        IntStream.range(0, NUMBER_OF_RANDOM_USERS).forEach(
                x -> userRepository.save(new User(UUID.randomUUID().toString(), Utils.randomUsername(animals(), adjectives())))
        );

        IntStream.range(0, 10).forEach(x ->
        {
            final Twaddle twaddle = new Twaddle(userMartin.getUserId(), Utils.randomTwaddle(animals(), adjectives()));
            twaddleRepository.save(twaddle);
        });
    }

    @Bean
    public AsyncTaskExecutor asyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public List<String> adjectives()
    {
        return Utils.resourceLines("adjectives.txt");
    }

    @Bean
    public List<String> animals()
    {
        return Utils.resourceLines("animals.txt");
    }
}
