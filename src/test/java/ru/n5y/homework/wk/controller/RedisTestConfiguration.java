package ru.n5y.homework.wk.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import redis.embedded.RedisServer;

@TestConfiguration
public class RedisTestConfiguration {

  private int port;

  @Bean(initMethod = "start", destroyMethod = "stop")
  public RedisServer embeddedRedis() {

    final RedisServer redisServer = RedisServer.builder()
        .port(6379)
        .setting("bind 127.0.0.1") // good for local development on Windows to prevent security popups
        .build();
    return redisServer;

  }

  @Bean
  @DependsOn("embeddedRedis")
  RedisConnectionFactory redisConnectionFactory() {
    final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("127.0.0.1", 6379);
    return new LettuceConnectionFactory(config);
  }

}
