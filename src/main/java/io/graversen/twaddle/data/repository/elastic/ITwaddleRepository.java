package io.graversen.twaddle.data.repository.elastic;

import io.graversen.twaddle.data.Twaddle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ITwaddleRepository extends ElasticsearchRepository<Twaddle, String>
{

}
