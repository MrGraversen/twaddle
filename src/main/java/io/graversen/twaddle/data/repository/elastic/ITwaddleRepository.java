package io.graversen.twaddle.data.repository.elastic;

import io.graversen.twaddle.data.document.Twaddle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ITwaddleRepository extends ElasticsearchRepository<Twaddle, String>
{
    Page<Twaddle> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    Page<Twaddle> findByHashTags(String hashTag, Pageable pageable);

    long countByUserId(String userId);
}
