package ru.n5y.homework.wk.controller;

import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class LikeControllerTest extends AbstractIntegrationTest {

  @Autowired
  private RedisMap<String, Long> likes;

  @BeforeEach
  void setUp() {
    likes.clear();
  }

  @Test
  @DisplayName("Get players likes successfully.")
  void getLikes() throws Exception {
    likes.put("1", 314L);
    mockMvc.perform(MockMvcRequestBuilders.get("/likes/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("314"));
  }

  @Test
  @DisplayName("Exception when no player to get likes.")
  void getLikesNotPlayer() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/likes/kolyan")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        .andExpect(MockMvcResultMatchers.content().string("Cannot find player with id 'kolyan'."));
  }

  @Test
  @DisplayName("Exception when like  player that not exists.")
  void likeNotPlayer() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/likes/kolyan")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        .andExpect(MockMvcResultMatchers.content().string("Cannot find player with id 'kolyan'."));
  }

  @Test
  @DisplayName("Like player successfully.")
  void likeSuccessful() throws Exception {
    likes.put("whalekit", 1000L);
    mockMvc.perform(MockMvcRequestBuilders.post("/likes/whalekit")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().bytes(new byte[0]));
    Assertions.assertThat(likes).containsOnly(Map.entry("whalekit", 1001L));
  }

}