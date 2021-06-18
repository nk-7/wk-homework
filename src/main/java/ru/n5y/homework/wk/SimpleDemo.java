package ru.n5y.homework.wk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.n5y.homework.wk.service.LikeService;
import ru.n5y.homework.wk.service.LocalMapCombinedService;
import ru.n5y.homework.wk.service.PlayerService;

public class SimpleDemo {

  private static final Logger log = LoggerFactory.getLogger(SimpleDemo.class);

  public static void main(String[] args) {
    final LocalMapCombinedService localMapCombinedService = new LocalMapCombinedService();
    // Вайринг на коленках=)
    final LikeService likeService = localMapCombinedService;
    final PlayerService playerService = localMapCombinedService;
    final String id = playerService.register();

    log.info("Players '{}' likes is '{}'.", id, likeService.getLikes(id));
    likeService.like(id);
    log.info("Players '{}' likes is '{}'.", id, likeService.getLikes(id));
  }
}
