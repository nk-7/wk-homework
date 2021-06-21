package ru.n5y.homework.wk.service;

import java.util.Objects;
import org.springframework.stereotype.Service;
import ru.n5y.homework.wk.repository.LikesRepository;

/**
 * Базовая реализация интерфейса {@link LikeService}.
 */
@Service
public class BaseLikesService implements LikeService {
  private final LikesRepository likesRepository;

  public BaseLikesService(LikesRepository likesRepository) {
    this.likesRepository = Objects.requireNonNull(likesRepository, "'likesRepository' should not be null.");
  }

  @Override
  public void like(String playerId) {
    likesRepository.like(playerId);

  }

  @Override
  public long getLikes(String playerId) {
    return likesRepository.getLikes(playerId);
  }
}
