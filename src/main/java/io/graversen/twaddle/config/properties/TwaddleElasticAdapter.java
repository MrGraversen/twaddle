package io.graversen.twaddle.config.properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.graversen.twaddle.data.Twaddle;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TwaddleElasticAdapter
{
    private static final String INDEX_NAME = "twaddles";

    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    private Mono<Twaddle> findById(String id)
    {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery(""));
        searchRequest.source(searchSourceBuilder);
        searchRequest.types("twaddle");

        return null;
    }
}
