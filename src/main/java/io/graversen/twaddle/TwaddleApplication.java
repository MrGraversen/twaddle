package io.graversen.twaddle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.graversen.twaddle.config")
public class TwaddleApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(TwaddleApplication.class, args);
    }
}

