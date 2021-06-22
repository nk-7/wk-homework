package ru.n5y.homework.wk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import ru.n5y.homework.wk.service.redis.LongSerializer;
import ru.n5y.homework.wk.service.redis.RedisConstants;

@Configuration
public class RedisConfiguration {
  @Bean
  RedisTemplate<String, Long> playersTemplate(RedisConnectionFactory redisConnectionFactory) {
    final RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new LongSerializer());
    return redisTemplate;
  }

  @Bean
  RedisAtomicLong sequence(RedisConnectionFactory redisConnectionFactory) {
    return new RedisAtomicLong(RedisConstants.SEQUENCE_ID, redisConnectionFactory);
  }

}
