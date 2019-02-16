package io.graversen.twaddle.data.model;

import lombok.Data;

@Data
public class UserModel
{
    private final String userId;
    private final String username;
    private final String createdAt;
    private final String twaddlesAdjective;
    private final long twaddles;
}
