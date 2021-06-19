package ru.n5y.homework.wk.service;

import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMap;
import org.redisson.client.codec.LongCodec;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.CompositeCodec;

class RedisServiceLikeServiceTest extends EmbeddedRedisTest {
  private LikeService likeService;
  private RMap<String, Long> players;

  @BeforeEach
  void setUp() {
    final CompositeCodec codec = new CompositeCodec(StringCodec.INSTANCE, LongCodec.INSTANCE);
    players = redissonClient.getMap(RedisServiceLikeService.PLAYERS_MAP, codec);

    players.clear();
    likeService = new RedisServiceLikeService(players);
    players.put("kolyan", 0L);
  }

  @Test
  void testLikesAndGetLikes() {
    Assertions.assertThat(likeService.getLikes("kolyan")).isZero();
    likeService.like("kolyan");
    Assertions.assertThat(likeService.getLikes("kolyan")).isOne();

  }

  @Nested
  @DisplayName("'like' method tests")
  class LikeTest {
    @Test
    @DisplayName("Should throws NPE when playerId is null.")
    void nullPlayerId() {
      Assertions.assertThatThrownBy(() -> likeService.like(null))
          .hasMessage("'playerId' should not be null.");
    }

    @Test
    @DisplayName("Should throw NoSuchPlayerException if playerId is unknown.")
    void likeNonexistentPlayer() {
      Assertions.assertThatThrownBy(() -> likeService.like("whalekit"))
          .hasMessage("Cannot find player 'whalekit'.");
    }

    @Test
    @DisplayName("Successful player like.")
    void successful() {
      likeService.like("kolyan");
      Assertions.assertThat(players).containsOnly(Map.entry("kolyan", 1L));
    }
  }

  @Nested
  @DisplayName("'getLikes' method tests")
  class GetLikesTest {

    @Test
    @DisplayName("Should throws NPE when playerId is null.")
    void nullPlayerId() {
      Assertions.assertThatThrownBy(() -> likeService.getLikes(null))
          .hasMessage("'playerId' should not be null.");
    }

    @Test
    @DisplayName("Should throw NoSuchPlayerException if playerId is unknown.")
    void likeNonexistentPlayer() {
      Assertions.assertThatThrownBy(() -> likeService.getLikes("whalekit"))
          .hasMessage("Cannot find player 'whalekit'.");
    }

    @Test
    void successful() {
      Assertions.assertThat(likeService.getLikes("kolyan")).isZero();

    }
  }

}