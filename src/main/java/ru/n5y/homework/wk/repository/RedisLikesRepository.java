package ru.n5y.homework.wk.repository;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.n5y.homework.wk.model.Player;

@Repository
public class RedisLikesRepository implements LikesRepository {

  private static final Logger log = LoggerFactory.getLogger(RedisLikesRepository.class);
  private final RedisTemplate<String, Player> redisTemplate;

  public RedisLikesRepository(RedisTemplate<String, Player> redisTemplate) {
    this.redisTemplate = Objects.requireNonNull(redisTemplate, "'redisTemplate' should not be null.");
  }

  @Override
  public void like(String playerId) {

    redisTemplate.opsForHash().increment("players:" + playerId, "likes", 1L);
  }

  @Override
  public long getLikes(String playerId) {
    return (long) redisTemplate.opsForHash().get("players:" + playerId, "likes");
  }
}
