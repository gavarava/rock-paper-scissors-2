package com.rps.app.adapters.postgres;

import static com.rps.app.adapters.postgres.SqlQueries.INSERT_PLAYER_SESSION_MAPPING_QUERY;
import static com.rps.app.adapters.postgres.SqlQueries.INSERT_SESSION_QUERY;
import static com.rps.app.adapters.postgres.SqlQueries.LIST_PLAYER_SESSION_MAPPINGS_QUERY;
import static com.rps.app.adapters.postgres.SqlQueries.LIST_SESSIONS_QUERY;

import com.rps.app.core.model.Game;
import com.rps.app.core.model.Player;
import com.rps.app.ports.GameRepository;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Slf4j
@AllArgsConstructor
public class PostgresGameRepository implements GameRepository {


  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public Game create(Game game) {
    var creationTimestamp = OffsetDateTime.now();
    var params = new MapSqlParameterSource()
        .addValue("sessionid", game.getId())
        .addValue("creationdate", creationTimestamp, Types.TIMESTAMP_WITH_TIMEZONE);
    namedParameterJdbcTemplate.update(INSERT_SESSION_QUERY, params);
    game.getPlayers().forEach(player -> {
      var playerSessionMappingParams = new MapSqlParameterSource()
          .addValue("sessionid", game.getId())
          .addValue("playername", player.getName());
      namedParameterJdbcTemplate.update(
          INSERT_PLAYER_SESSION_MAPPING_QUERY,
          playerSessionMappingParams);
    });
    return game;
  }

  @Override
  public Game update(Game game) {
    throw new NotImplementedException();
  }

  @Override
  public Optional<Game> findById(String sessionId) {
    List<Player> players = namedParameterJdbcTemplate
        .query(LIST_PLAYER_SESSION_MAPPINGS_QUERY,
            new MapSqlParameterSource().addValue("sessionid", sessionId),
            (resultSet, i) -> Player
                .builder()
                .name(resultSet.getString("name"))
                .creationDate(resultSet.getObject("creation_date", OffsetDateTime.class))
                .build());

    log.info("Players {} in session {}", players, sessionId);

    var sessions = namedParameterJdbcTemplate
        .query(LIST_SESSIONS_QUERY, new MapSqlParameterSource().addValue("sessionid", sessionId), (resultSet, i) -> Game
            .builder()
            .id(resultSet.getString("session_id"))
            .players(new HashSet<>(players))
            .build()
        );
    return Optional.of(sessions.get(0));
  }
}
