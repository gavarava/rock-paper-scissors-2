package com.rps.app;

import com.rps.app.adapters.postgres.PostgresPlayersRepository;
import com.rps.app.core.services.PlayersService;
import com.rps.app.ports.PlayersRepository;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@EnableConfigurationProperties(DatabaseProperties.class)
class AppConfiguration {

  @Bean
  PlayersRepository postgresPlayersRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new PostgresPlayersRepository(namedParameterJdbcTemplate);
  }

  @Bean
  PlayersService playersService(PlayersRepository playersRepository) {
    return new PlayersService(playersRepository);
  }

  @Bean
  public DataSource dataSource() {
    return DataSourceBuilder.create()
        .username("postgres")
        .password("password123")
        .url("jdbc:postgresql://localhost:5432/postgres")
        .driverClassName("org.postgresql.Driver")
        .build();
  }
}
