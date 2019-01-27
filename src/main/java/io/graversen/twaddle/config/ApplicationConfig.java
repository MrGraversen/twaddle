package io.graversen.twaddle.config;

import io.graversen.twaddle.data.Twaddle;
import io.graversen.twaddle.data.repository.elastic.ITwaddleRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@ComponentScan(basePackages = {})
public class ApplicationConfig implements ApplicationListener<ApplicationReadyEvent>
{
    private final ITwaddleRepository twaddleRepository;

    public ApplicationConfig(ITwaddleRepository twaddleRepository)
    {
        this.twaddleRepository = twaddleRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent)
    {
        twaddleRepository.save(new Twaddle("1", "martin-1337", LocalDateTime.now(), "This works!"));
    }
}
