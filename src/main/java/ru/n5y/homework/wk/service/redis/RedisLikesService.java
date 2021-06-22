package ru.n5y.homework.wk.service.redis;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.RedisMap;
import ru.n5y.homework.wk.service.LikeService;
import ru.n5y.homework.wk.service.NoSuchPlayerException;

/**
 * Реализация {@link LikeService} с использованием {@link RedisTemplate}.
 */
public class RedisLikesService implements LikeService {
  private static final Logger log = LoggerFactory.getLogger(RedisLikesService.class);
  private final RedisMap<String, Long> likes;

  public RedisLikesService(RedisMap<String, Long> likes) {
    this.likes = Objects.requireNonNull(likes, "'likes' should not be null.");
  }

  @Override
  public void like(String playerId) {
    if (likes.containsKey(playerId)) {
      final Long newLikesCount = likes.increment(playerId, 1L);
      log.info("Player '{}' has been liked, likes count is '{}'.", playerId, newLikesCount);
    } else {
      log.warn("Cannot find player '{}' -  throw NoSuchPlayerException.", playerId);
      throw new NoSuchPlayerException(String.format("Cannot find player with id '%s'.", playerId));
    }

  }

  @Override
  public long getLikes(String playerId) {
    final Long likesCount = likes.get(playerId);
    if (likesCount == null) {
      log.warn("Cannot find player '{}' -  throw NoSuchPlayerException.", playerId);
      throw new NoSuchPlayerException(String.format("Cannot find player with id '%s'.", playerId));
    }
    return likesCount;
  }

}
