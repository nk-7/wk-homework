
package ru.n5y.homework.wk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.RedisMap;
import ru.n5y.homework.wk.service.LikeService;
import ru.n5y.homework.wk.service.PlayerService;
import ru.n5y.homework.wk.service.redis.RedisConstants;
import ru.n5y.homework.wk.service.redis.RedisLikesService;
import ru.n5y.homework.wk.service.redis.RedisPlayerService;

@Configuration
public class RedisConfiguration {

  private final RedisConnectionFactory redisConnectionFactory;

  public RedisConfiguration(RedisConnectionFactory redisConnectionFactory) {
    this.redisConnectionFactory = redisConnectionFactory;
  }

  @Bean
  RedisTemplate<String, Long> likesTemplate() {
    final RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(RedisSerializer.string());
    redisTemplate.setHashKeySerializer(RedisSerializer.string());
    redisTemplate.setHashValueSerializer(new GenericToStringSerializer<>(Long.class));
    return redisTemplate;
  }

  @Bean
  RedisAtomicLong sequence() {
    return new RedisAtomicLong(RedisConstants.SEQUENCE_ID, redisConnectionFactory);
  }

  @Bean
  RedisMap<String, Long> likesMap() {
    return new DefaultRedisMap<>(likesTemplate().boundHashOps(RedisConstants.HASH_LIKES));
  }

  @Bean
  LikeService redisLikeService() {
    return new RedisLikesService(likesMap());
  }

  @Bean
  PlayerService redisPlayerService() {
    return new RedisPlayerService(likesMap(), sequence());
  }

}
