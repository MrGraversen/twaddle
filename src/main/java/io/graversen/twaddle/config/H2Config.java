package io.graversen.twaddle.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"io.graversen.twaddle.data.repository.jpa"})
public class H2Config
{

}
