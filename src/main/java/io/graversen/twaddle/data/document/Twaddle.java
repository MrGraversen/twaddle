package io.graversen.twaddle.data.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Document(indexName = "twaddles", type = "twaddle")
public class Twaddle
{
    @Id
    private String id = UUID.randomUUID().toString();

    @NonNull
    private String userId;

    @Field(type = FieldType.Date)
    private LocalDateTime createdAt = LocalDateTime.now();

    @NonNull
    private String text;

    @Field(type = FieldType.Nested)
    private List<Hashtag> hashtags;
}
