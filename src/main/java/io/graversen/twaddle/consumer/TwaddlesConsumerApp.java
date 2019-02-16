package io.graversen.twaddle.consumer;

import io.graversen.twaddle.config.TwaddleChannels;
import io.graversen.twaddle.data.document.Twaddle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;

//@EnableBinding(TwaddlesConsumerApp.Channels.class)
//@SpringBootApplication
//public class TwaddlesConsumerApp
//{
//    public static void main(String[] args)
//    {
//        new SpringApplicationBuilder(TwaddlesConsumerApp.class).web(WebApplicationType.NONE).run(args);
//    }
//
//    @StreamListener("twaddles")
//    public void consumeTwaddle(Twaddle twaddle)
//    {
//        System.out.println(String.format("Received Twaddle: %s", twaddle.getText()));
//    }
//
//    interface Channels
//    {
//        @Input
//        SubscribableChannel twaddles();
//    }
//}
