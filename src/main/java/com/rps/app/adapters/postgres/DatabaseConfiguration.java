package com.rps.app.adapters.postgres;

import com.rps.app.ports.GameRepository;
import com.rps.app.ports.PlayersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfiguration {

  @Bean
  PlayersRepository postgresPlayersRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new PostgresPlayersRepository(namedParameterJdbcTemplate);
  }

  @Bean
  GameRepository gameRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    //TODO add profile to return new TransientGameRepository(new HashMap<>());
    return new PostgresGameRepository(namedParameterJdbcTemplate);
  }

}
