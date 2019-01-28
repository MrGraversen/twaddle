package io.graversen.twaddle.data.repository.elastic;

import io.graversen.twaddle.data.document.Hashtag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IHashTagRepository extends ElasticsearchRepository<Hashtag, String>
{

}
