package ru.n5y.homework.wk.service;

import ru.n5y.homework.wk.model.Player;

/**
 * Сервис для работы с пользователями. По условию не требовалось.
 */
public interface PlayerService {
  /**
   * Регистрация нового пользователя.
   *
   * @return Вновь созданный пользователь.
   */
  Player register();

  /**
   * Получение списка существующих пользователей.
   *
   * @return
   */
  Iterable<Player> players();
}
