package io.graversen.twaddle;

import io.graversen.twaddle.config.TwaddleChannels;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication(
        scanBasePackages = "io.graversen.twaddle.config",
        exclude = {ElasticsearchDataAutoConfiguration.class}
)
@EnableBinding(TwaddleChannels.class)
public class TwaddleApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(TwaddleApplication.class, args);
    }
}

