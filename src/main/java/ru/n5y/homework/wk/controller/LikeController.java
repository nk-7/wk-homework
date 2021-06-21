package ru.n5y.homework.wk.controller;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.n5y.homework.wk.service.LikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {
  private static final Logger log = LoggerFactory.getLogger(LikeController.class);
  private final LikeService likeService;

  public LikeController(LikeService likeService) {
    this.likeService = Objects.requireNonNull(likeService, "'likeService' should be not null.");
  }

  @GetMapping("/{playerId}")
  public ResponseEntity<Long> get(@PathVariable("playerId") String playerId) {
    log.debug("GET request for likes of player '{}'.", playerId);
    return ResponseEntity.ok(likeService.getLikes(playerId));
  }

  @PostMapping("/{playerId}")
  public ResponseEntity<Void> like(@PathVariable String playerId) {
    log.debug("PUT request to like player '{}'.", playerId);
    likeService.like(playerId);
    return ResponseEntity.ok().build();
  }
}
