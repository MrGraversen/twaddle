package io.graversen.twaddle.config.properties;

import lombok.Data;
import org.apache.http.HttpHost;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchProperties
{
    private List<String> hosts;
    private int connectTimeout;
    private int connectionRequestTimeout;
    private int socketTimeout;
    private int maxRetryTimeoutMillis;

    public HttpHost[] hosts()
    {
        return hosts.stream()
                .map(HttpHost::create)
                .toArray(HttpHost[]::new);
    }
}

