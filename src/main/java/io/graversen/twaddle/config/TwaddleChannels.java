package io.graversen.twaddle.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface TwaddleChannels
{
    @Input
    MessageChannel twaddles();
}
