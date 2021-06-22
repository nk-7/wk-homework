package ru.n5y.homework.wk.service.redis;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import ru.n5y.homework.wk.service.PlayerService;

/**
 * Реализация {@link PlayerService} с использованием {@link RedisTemplate}.
 */

@Service
public class RedisPlayerService implements PlayerService {
  private static final Logger log = LoggerFactory.getLogger(RedisPlayerService.class);
  private final BoundHashOperations<String, String, Long> hashOperations;
  private final RedisAtomicLong idSequence;

  public RedisPlayerService(RedisTemplate<String, Long> redisTemplate, RedisAtomicLong idSequence) {
    this.idSequence = Objects.requireNonNull(idSequence, "'idSequence' should not be null.");
    this.hashOperations = Objects.requireNonNull(redisTemplate, "'redisTemplate' should not be null.")
        .boundHashOps(RedisConstants.HASH_LIKES);
  }

  @Override
  public String register() {
    final String playerId = String.valueOf(idSequence.incrementAndGet());
    log.debug("Got id for new player '{}', from sequence '{}'.", playerId, RedisConstants.SEQUENCE_ID);
    hashOperations.put(playerId, 0L);
    log.debug("New player '{}' has been added to hash '{}'.", playerId, RedisConstants.HASH_LIKES);
    return playerId;
  }

  @Override
  public Iterable<String> players() {
    return hashOperations.keys();
  }
}
