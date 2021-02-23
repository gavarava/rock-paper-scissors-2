package com.rps.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rps.app.adapters.memory.TransientGameRepository;
import com.rps.app.adapters.postgres.PostgresPlayersRepository;
import com.rps.app.core.services.DefaultRockPaperScissorsService;
import com.rps.app.core.services.PlayersService;
import com.rps.app.core.services.RockPaperScissorsService;
import com.rps.app.ports.GameRepository;
import com.rps.app.ports.PlayersRepository;
import java.util.HashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Profile({"prod"})
@Configuration
class AppConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Bean
  PlayersRepository postgresPlayersRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new PostgresPlayersRepository(namedParameterJdbcTemplate);
  }

  @Bean
  GameRepository gameRepository() {
    return new TransientGameRepository(new HashMap<>());
  }

  @Bean
  RockPaperScissorsService rockPaperScissorsService(GameRepository gameRepository) {
    return new DefaultRockPaperScissorsService(gameRepository);
  }

  @Bean
  PlayersService playersService(PlayersRepository playersRepository) {
    return new PlayersService(playersRepository);
  }
}
