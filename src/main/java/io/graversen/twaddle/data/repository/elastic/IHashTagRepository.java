package io.graversen.twaddle.data.repository.elastic;

import io.graversen.twaddle.data.Hashtag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IHashTagRepository extends ElasticsearchRepository<Hashtag, String>
{

}
