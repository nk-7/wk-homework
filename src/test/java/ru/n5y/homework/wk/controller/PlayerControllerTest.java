package ru.n5y.homework.wk.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class PlayerControllerTest extends AbstractIntegrationTest {

  @Autowired
  private RedisMap<String, Long> likes;

  @BeforeEach
  void setUp() {
    likes.clear();
  }

  @Test
  @DisplayName("Get payers - no players.")
  void getPlayersWhenNoPlayers() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/players")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  @Test
  @DisplayName("Get payers - success.")
  void getPlayers() throws Exception {
    likes.put("TestPlayer", 0L);
    likes.put("Kolyan", 314L);
    likes.put("xo/7a", 317987L);
    mockMvc.perform(MockMvcRequestBuilders.get("/players")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("TestPlayer")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1]", Matchers.is("Kolyan")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2]", Matchers.is("xo/7a")));

  }

  @Test
  @DisplayName("Register player.")
  void register() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/players")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("1"));

    mockMvc.perform(MockMvcRequestBuilders.post("/players")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("2"));
  }

}