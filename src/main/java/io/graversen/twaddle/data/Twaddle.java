package io.graversen.twaddle.data;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Twaddle
{
    private String id;
    private String username;
    private LocalDateTime createdAt;
    private String text;
}
