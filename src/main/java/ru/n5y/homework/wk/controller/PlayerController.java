package ru.n5y.homework.wk.controller;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.n5y.homework.wk.service.PlayerService;

/**
 * Контроллер для работы с игроками. Позволяет получить список пользователей, а также зарегистрировать нового.
 */
@RestController
@RequestMapping("/players")
public class PlayerController {
  private static final Logger log = LoggerFactory.getLogger(PlayerController.class);
  private final PlayerService playerService;

  public PlayerController(PlayerService playerService) {
    this.playerService = Objects.requireNonNull(playerService, "'playerService' should not be null.");
  }

  /**
   * Получение списка пользователей.
   *
   * @return Список пользователей.
   */
  @GetMapping
  public ResponseEntity<Iterable<String>> get() {
    log.debug("GET request for players list.");
    return ResponseEntity.ok(playerService.players());
  }

  /**
   * Регистрация нового пользователя.
   *
   * @return Вновь зарегистрированный пользователь.
   */
  @PostMapping
  public ResponseEntity<String> register() {
    log.debug("POST request for registration.");
    final String playerId = playerService.register();
    log.info("New player has been registered with id '{}'.", playerId);
    return ResponseEntity.ok(playerId);
  }
}
