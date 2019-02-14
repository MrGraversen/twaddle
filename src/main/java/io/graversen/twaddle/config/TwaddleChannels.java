package io.graversen.twaddle.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface TwaddleChannels
{
    @Input
    SubscribableChannel twaddles();
}
