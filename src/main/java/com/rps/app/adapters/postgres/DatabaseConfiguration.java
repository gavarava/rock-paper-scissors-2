package com.rps.app.adapters.postgres;

import com.rps.app.ports.SessionsRepository;
import com.rps.app.ports.PlayersRepository;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfiguration {

  @Bean
  PlayersRepository postgresPlayersRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new PostgresPlayersRepository(namedParameterJdbcTemplate);
  }

  @Bean
  @Profile(value = "!transient")
  SessionsRepository sessionsRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new PostgresSessionsRepository(namedParameterJdbcTemplate);
  }
}
