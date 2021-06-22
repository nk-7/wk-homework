package ru.n5y.homework.wk.service;

/**
 * Сервис для работы с пользователями. По условию не требовалось.
 */
public interface PlayerService {
  /**
   * Регистрация нового пользователя.
   *
   * @return Вновь созданный пользователь.
   */
  String register();

  /**
   * Получение списка существующих пользователей.
   *
   * @return
   */
  Iterable<String> players();
}
