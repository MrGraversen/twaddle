package io.graversen.twaddle.data.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Document(indexName = "hashtags", type = "hashtag")
public class Hashtag
{
    @Id
    private String id = UUID.randomUUID().toString();

    @NonNull
    private String tag;
}
