package io.graversen.twaddle.data.model;

import lombok.Data;

@Data
public class TwaddleModel
{
    private final String text;
    private final String createdAt;
    private String username;
}
