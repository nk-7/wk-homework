package ru.n5y.homework.wk.repository;

import ru.n5y.homework.wk.service.NoSuchPlayerException;

public interface LikesRepository {

  /**
   * Добавить лайк игроку.
   * <p>
   * В условиях не было оговорено, что делать если пользователя нет. Возьму на себя смелость бросить исключение.
   *
   * @param playerId ID игрока.
   * @throws NoSuchPlayerException если игрока с таким playerId нет.
   * @throws NullPointerException  если playerId = null.
   */
  void like(String playerId);

  /**
   * Получить количество лайков у игрока.
   * <p>
   * Было не оговорено, что делать, если пользователя нет. Буду бросать исключение.
   *
   * @param playerId - ID игрока.
   * @return Количество лайков.
   * @throws NoSuchPlayerException если пытаемся лайкнуть несуществующего пользователя.
   * @throws NullPointerException  если playerId = null.
   */
  long getLikes(String playerId);


}
