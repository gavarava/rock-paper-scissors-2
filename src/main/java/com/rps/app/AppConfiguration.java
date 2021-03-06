package com.rps.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rps.app.adapters.memory.TransientSessionsRepository;
import com.rps.app.adapters.postgres.PostgresSessionsRepository;
import com.rps.app.core.metrics.RegisteredPlayersCounter;
import com.rps.app.core.metrics.StartedSessionsCounter;
import com.rps.app.core.services.DefaultRockPaperScissorsService;
import com.rps.app.core.services.PlayersService;
import com.rps.app.core.services.RockPaperScissorsService;
import com.rps.app.ports.SessionsRepository;
import com.rps.app.ports.PlayersRepository;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.HashMap;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class AppConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Bean
  RockPaperScissorsService rockPaperScissorsService(SessionsRepository sessionsRepository, StartedSessionsCounter startedSessionsCounter) {
    return new DefaultRockPaperScissorsService(sessionsRepository, startedSessionsCounter);
  }

  @Bean
  PlayersService playersService(PlayersRepository playersRepository, RegisteredPlayersCounter registeredPlayersCounter) {
    return new PlayersService(playersRepository, registeredPlayersCounter);
  }

  @Bean
  @Profile(value = "transient")
  SessionsRepository sessionsRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new TransientSessionsRepository(new HashMap<>());
  }

  @Bean
  MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
    return registry -> registry.config().commonTags("application", "rps2-app", "hikaricp", "rps2-hikaricp");
  }

  @Bean
  RegisteredPlayersCounter registeredPlayersCounter(MeterRegistry meterRegistry) {
    return new RegisteredPlayersCounter(meterRegistry);
  }

  @Bean
  StartedSessionsCounter startedSessionsCounter(MeterRegistry meterRegistry) {
    return new StartedSessionsCounter(meterRegistry);
  }
}
