package ru.n5y.homework.wk.service;

import java.util.Objects;
import org.redisson.api.RMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisServiceLikeService implements LikeService {
  private static final Logger log = LoggerFactory.getLogger(RedisServiceLikeService.class);
  public static String PLAYERS_MAP = "players";
  private final RMap<String, Long> players;

  public RedisServiceLikeService(RMap<String, Long> players) {
    this.players = Objects.requireNonNull(players, "'players' should not be null.");
  }

  @Override
  public void like(String playerId) {
    checkPlayerIdIsNotNullAndExists(playerId);
    final Long likes = players.addAndGet(playerId, 1L);
    log.debug("Player '{}' was liked. Count of likes now is '{}'.", playerId, likes);

  }

  @Override
  public long getLikes(String playerId) {
    checkPlayerIdIsNotNullAndExists(playerId);
    return players.get(playerId);
  }

  private void checkPlayerIdIsNotNullAndExists(String playerId) {
    Objects.requireNonNull(playerId, "'playerId' should not be null.");
    if (!players.containsKey(playerId)) {
      throw new NoSuchPlayerException(String.format("Cannot find player '%s'.", playerId));
    }
  }
}
