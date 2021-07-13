package com.rps.app.adapters.postgres;

import static com.rps.app.adapters.postgres.SqlQueries.INSERT_PLAYER_SESSION_MAPPING_QUERY;
import static com.rps.app.adapters.postgres.SqlQueries.INSERT_SESSION_QUERY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rps.app.core.model.Game;
import com.rps.app.core.model.Player;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

class PostgresSessionsRepositoryTest {

  @Mock
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  PostgresSessionsRepository postgresSessionsRepository;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    when(namedParameterJdbcTemplate.update(any(), any(MapSqlParameterSource.class))).thenReturn(1);
    postgresSessionsRepository = new PostgresSessionsRepository(namedParameterJdbcTemplate);
  }


  @Test
  void shouldUpdatePlayerSessionsMapping_whenCreatingSession() {
    var playerCreationTime = OffsetDateTime.now();
    var playerName = "PlayerX";
    var sessionId = "SESSIONID";
    var player = Player.builder().name(playerName).creationDate(playerCreationTime).build();
    var session = Game.builder()
        .id(sessionId)
        .players(Set.of(player))
        .build();
    postgresSessionsRepository.create(session);

    var parameterSourceArgumentCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
    var stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    verify(namedParameterJdbcTemplate, times(2)).update(stringArgumentCaptor.capture(), parameterSourceArgumentCaptor.capture());
    assertThat(stringArgumentCaptor.getAllValues())
        .containsExactlyInAnyOrder(INSERT_SESSION_QUERY, INSERT_PLAYER_SESSION_MAPPING_QUERY);

    assertThat(parameterSourceArgumentCaptor.getAllValues().stream()
        .flatMap(mapSqlParameterSource -> mapSqlParameterSource.getValues().values().stream())
        .collect(Collectors.toList()))
        .containsAnyOf(playerCreationTime, playerName, sessionId);
  }
}
