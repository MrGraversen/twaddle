package io.graversen.twaddle.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface TwaddleChannels
{
    @Input
    SubscribableChannel twaddlesInput();

    @Output
    MessageChannel twaddlesOutput();
}
