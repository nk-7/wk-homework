package ru.n5y.homework.wk.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import redis.embedded.RedisServer;

/**
 * Базовый класс для интеграционных тестов, с embedded Redis.
 * <p>
 * Запускает и останавливает встроенный редис.
 */
public abstract class EmbeddedRedisTest {
  protected static RedisServer redisServer;
  protected static RedissonClient redissonClient;

  @BeforeAll
  static void beforeAll() {
    RedisServer redisServer = RedisServer.builder()
        .port(6379)
        .setting("bind 127.0.0.1") // good for local development on Windows to prevent security popups
        .build();

    redisServer.start();
    final Config config = new Config();
    config.useSingleServer().setAddress("redis://127.0.0.1:6379");
    redissonClient = Redisson.create(config);

  }

  @AfterAll
  static void afterAll() {
    if (redisServer != null) {
      redisServer.stop();
    }
  }
}
