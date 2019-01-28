package io.graversen.twaddle.data.document;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "hashtags", type = "hashtag")
public class Hashtag
{
    @Id
    private String id;

    @NonNull
    private String tagName;
}
