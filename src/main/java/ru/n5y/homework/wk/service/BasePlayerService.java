package ru.n5y.homework.wk.service;

import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.n5y.homework.wk.model.Player;
import ru.n5y.homework.wk.repository.PlayersRepository;

/**
 * Базовая реализация {@link PlayerService}.
 */
@Service
public class BasePlayerService implements PlayerService {
  private static final Logger log = LoggerFactory.getLogger(BasePlayerService.class);
  private final PlayersRepository playersRepository;

  public BasePlayerService(PlayersRepository playersRepository) {
    this.playersRepository = Objects.requireNonNull(playersRepository, "'repository' should not be null.");
  }

  @Override
  public Player register() {
    final Player player = new Player(UUID.randomUUID().toString(), 0L);
    playersRepository.save(player);
    return player;
  }

  @Override
  public Iterable<Player> players() {
    return playersRepository.findAll();
  }
}
