package io.graversen.twaddle.data;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Value
@Document(indexName = "twaddles", type = "twaddle")
public class Twaddle
{
    @Id
    private String id;
    private String username;
    private LocalDateTime createdAt;
    private String text;
}
