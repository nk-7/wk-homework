package ru.n5y.homework.wk.service.redis;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.redis.support.collections.RedisMap;
import ru.n5y.homework.wk.service.PlayerService;

/**
 * Реализация {@link PlayerService} с использованием {@link RedisTemplate}.
 */

public class RedisPlayerService implements PlayerService {
  private static final Logger log = LoggerFactory.getLogger(RedisPlayerService.class);
  private final RedisMap<String, Long> likes;
  private final RedisAtomicLong idSequence;

  public RedisPlayerService(RedisMap<String, Long> likes, RedisAtomicLong idSequence) {
    this.likes = Objects.requireNonNull(likes, "'likes' should not be null.");
    this.idSequence = Objects.requireNonNull(idSequence, "'idSequence' should not be null.");

  }

  @Override
  public String register() {
    final String playerId = String.valueOf(idSequence.incrementAndGet());
    log.debug("Got id for new player '{}', from sequence '{}'.", playerId, RedisConstants.SEQUENCE_ID);
    likes.put(playerId, 0L);
    log.debug("New player '{}' has been added to hash '{}'.", playerId, RedisConstants.HASH_LIKES);
    return playerId;
  }

  @Override
  public Iterable<String> players() {
    return likes.keySet();
  }
}
