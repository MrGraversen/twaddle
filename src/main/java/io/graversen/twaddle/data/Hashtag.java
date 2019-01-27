package io.graversen.twaddle.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "twaddles", type = "hashtag")
public class Hashtag
{
    @Id
    private String id;

    private String tagName;
}
