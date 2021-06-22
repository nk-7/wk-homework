package ru.n5y.homework.wk.service.redis;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.n5y.homework.wk.service.LikeService;
import ru.n5y.homework.wk.service.NoSuchPlayerException;

/**
 * Реализация {@link LikeService} с использованием {@link RedisTemplate}.
 */
@Service
public class RedisLikesService implements LikeService {
  private static final Logger log = LoggerFactory.getLogger(RedisLikesService.class);
  private final BoundHashOperations<String, String, Long> hashOperations;

  public RedisLikesService(RedisTemplate<String, Long> redisTemplate) {
    this.hashOperations = Objects.requireNonNull(redisTemplate, "'redisTemplate' should not be null.")
        .boundHashOps(RedisConstants.HASH_LIKES);
  }

  @Override
  public void like(String playerId) {
    final Boolean hasKey = hashOperations.hasKey(playerId);
    if (hasKey != null && hasKey) {
      final Long likes = hashOperations.increment(playerId, 1L);
      log.info("Player '{}' has been liked, likes count is '{}'.", playerId, likes);
    } else {
      log.warn("Cannot find player '{}' -  throw NoSuchPlayerException.", playerId);
      throw new NoSuchPlayerException(String.format("Cannot find player with id '%s'.", playerId));
    }

  }

  @Override
  public long getLikes(String playerId) {
    final Long likes = hashOperations.get(playerId);
    if (likes == null) {
      throw new NoSuchPlayerException(String.format("Cannot find player with id '%s'.", playerId));
    }
    return likes;
  }

}
