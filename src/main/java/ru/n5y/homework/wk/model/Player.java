package ru.n5y.homework.wk.model;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("players")
public class Player {
  @Id
  private String playerId;
  private Long likes;

  public Player(String playerId, Long likes) {
    this.playerId = playerId;
    this.likes = likes;
  }

  public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public Long getLikes() {
    return likes;
  }

  public void setLikes(Long likes) {
    this.likes = likes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Player player = (Player) o;
    return playerId.equals(player.playerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerId);
  }

  @Override
  public String toString() {
    return "Player{" +
        "playerId='" + playerId + '\'' +
        ", likes=" + likes +
        '}';
  }
}
