package io.graversen.twaddle.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableElasticsearchRepositories(basePackages = "io.graversen.twaddle.data.repository.elastic")
public class ElasticsearchConfig
{
    @Value("${elasticsearch.address}")
    private String elasticAddress;
    @Value("${elasticsearch.port}")
    private int elasticPort;
    @Value("${elasticsearch.cluster-name}")
    private String elasticClusterName;

    @Bean
    public Client client()
    {
        final TransportAddress transportAddress = new TransportAddress(elasticHost(), elasticPort);
        final Settings settings = Settings.builder().put("client.transport.sniff", false ).put("cluster.name", elasticClusterName).build();

        return new PreBuiltTransportClient(settings).addTransportAddress(transportAddress);
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate()
    {
        return new ElasticsearchTemplate(client());
    }

    private InetAddress elasticHost()
    {
        try
        {
            return InetAddress.getByName(elasticAddress);
        }
        catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
    }
}
