package ru.n5y.homework.wk.service;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.LoggerFactory;

public class LocalMapCombinedService implements PlayerService, LikeService {
  private static final org.slf4j.Logger log = LoggerFactory.getLogger(LocalMapCombinedService.class);
  private final ConcurrentMap<String, Long> likes = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong();

  @Override
  public void like(String playerId) {
    checkPlayerIdIsNotNullAndExists(playerId);
    likes.computeIfPresent(playerId, (id, l) -> {
      l++;
      log.debug("Player '{}' was liked. Count of likes now is '{}'.", id, l);
      return l;
    });
  }

  @Override
  public long getLikes(String playerId) {
    checkPlayerIdIsNotNullAndExists(playerId);
    return likes.get(playerId);
  }

  @Override
  public String register() {
    final String playerId = String.valueOf(sequence.incrementAndGet());
    likes.put(playerId, 0L);
    log.debug("New playerId '{}' has been registered.", playerId);
    return playerId;
  }

  private void checkPlayerIdIsNotNullAndExists(String playerId) {
    Objects.requireNonNull(playerId, "'playerId' is null.");
    if (!likes.containsKey(playerId)) {
      throw new NoSuchPlayerException(String.format("Cannot find player '%s'.", playerId));
    }
  }
}
