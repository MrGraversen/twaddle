package io.graversen.twaddle.config;

import io.graversen.twaddle.data.document.Hashtag;
import io.graversen.twaddle.data.document.Twaddle;
import io.graversen.twaddle.data.entity.User;
import io.graversen.twaddle.data.repository.elastic.IHashTagRepository;
import io.graversen.twaddle.data.repository.elastic.ITwaddleRepository;
import io.graversen.twaddle.data.repository.jpa.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {})
@RequiredArgsConstructor
public class ApplicationConfig implements ApplicationListener<ApplicationReadyEvent>
{
    private final ITwaddleRepository twaddleRepository;
    private final IHashTagRepository hashTagRepository;
    private final IUserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent)
    {
        twaddleRepository.deleteAll();
        hashTagRepository.deleteAll();

        hashTagRepository.save(new Hashtag("yolo"))
        twaddleRepository.save(new Twaddle("martin-1337", "This works!"));
        userRepository.save(new User("martin-1337", "martin"));
    }
}
