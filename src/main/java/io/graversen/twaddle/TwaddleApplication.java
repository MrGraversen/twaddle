package io.graversen.twaddle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = "io.graversen.twaddle.config",
        exclude = {ElasticsearchDataAutoConfiguration.class}
)
public class TwaddleApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(TwaddleApplication.class, args);
    }
}

