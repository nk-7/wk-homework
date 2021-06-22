package ru.n5y.homework.wk.service.redis;

import java.nio.ByteBuffer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class LongSerializer implements RedisSerializer<Long> {
  @Override
  public byte[] serialize(Long aLong) throws SerializationException {
    final ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
    bb.putLong(aLong);
    return bb.array();
  }

  @Override
  public Long deserialize(byte[] bytes) throws SerializationException {
    final ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
    bb.put(bytes);
    bb.flip();
    return bb.getLong();
  }
}
