package com.rps.app.adapters.postgres;

import static com.rps.app.adapters.postgres.SqlQueries.INSERT_MOVE_QUERY;
import static com.rps.app.adapters.postgres.SqlQueries.INSERT_PLAYER_SESSION_MAPPING_QUERY;
import static com.rps.app.adapters.postgres.SqlQueries.INSERT_SESSION_QUERY;
import static com.rps.app.adapters.postgres.SqlQueries.LIST_MOVES_QUERY;
import static com.rps.app.adapters.postgres.SqlQueries.LIST_PLAYER_SESSION_MAPPINGS_QUERY;
import static com.rps.app.adapters.postgres.SqlQueries.LIST_SESSIONS_QUERY;
import static com.rps.app.adapters.postgres.SqlQueries.UPDATE_SESSION_QUERY;

import com.rps.app.core.model.Move;
import com.rps.app.core.model.Move.Type;
import com.rps.app.core.model.Player;
import com.rps.app.core.model.Session;
import com.rps.app.ports.SessionsRepository;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Slf4j
@AllArgsConstructor
public class PostgresSessionsRepository implements SessionsRepository {


  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public Session create(Session session) {
    var creationTimestamp = OffsetDateTime.now();
    var params = new MapSqlParameterSource()
        .addValue("sessionid", session.getId())
        .addValue("creationdate", creationTimestamp, Types.TIMESTAMP_WITH_TIMEZONE);
    namedParameterJdbcTemplate.update(INSERT_SESSION_QUERY, params);
    addPlayerToSessionMapping(session, null);
    return session;
  }

  @Override
  public Session update(Session sessionToUpdate) {
    var sessionOptional = findById(sessionToUpdate.getId());
    if (sessionOptional.isPresent()) {
      var foundSession = sessionOptional.get();
      if (foundSession.getPlayers().size() < sessionToUpdate.getPlayers().size()) {
        foundSession.getPlayers().forEach(existingPlayer -> addPlayerToSessionMapping(sessionToUpdate, existingPlayer.getName()));
      }
      if (foundSession.getMoves() != null && sessionToUpdate.getMoves() != null) {
        updateMoves(foundSession, sessionToUpdate);
      }
      addWinnerIfExists(sessionToUpdate, foundSession);
    } else {
      throw new RuntimeException("Session with id < " + sessionToUpdate.getId() + " > could not be found");
    }
    return findById(sessionToUpdate.getId())
        .orElseThrow(() -> new IllegalStateException("Unable to find Session with id = " + sessionToUpdate.getId()));
  }

  private void addWinnerIfExists(Session sessionToUpdate, Session foundSession) {
    var winner = sessionToUpdate.getWinner();
    if (winner != null) {
      var params = new MapSqlParameterSource()
          .addValue("sessionid", sessionToUpdate.getId())
          .addValue("winner", winner.getName());
      var update = namedParameterJdbcTemplate.update(UPDATE_SESSION_QUERY, params);
      if (update == 0) {
        log.warn("Did not update winner {} in session {}", winner.getName(), foundSession.getId());
      }
    }
  }

  private void updateMoves(Session foundSession, Session sessionToUpdate) {
    if (foundSession.getMoves().size() < sessionToUpdate.getMoves().size()) {
      addMove(sessionToUpdate);
    }
  }

  private void addMove(Session sessionToUpdate) {
    sessionToUpdate.getLatestMove().ifPresent(latestMoveToBeAdded -> {
      var params = new MapSqlParameterSource()
          .addValue("sessionid", sessionToUpdate.getId())
          .addValue("type", latestMoveToBeAdded.getType().name())
          .addValue("playedAt", latestMoveToBeAdded.getPlayedAt())
          .addValue("playername", latestMoveToBeAdded.getPlayer().getName());
      namedParameterJdbcTemplate.update(
          INSERT_MOVE_QUERY,
          params);
    });
  }

  private void addPlayerToSessionMapping(Session session, String existingPlayerName) {
    session.getPlayers().forEach(player -> {
      if (!player.getName().equals(existingPlayerName)) {
        var playerSessionMappingParams = new MapSqlParameterSource()
            .addValue("sessionid", session.getId())
            .addValue("playername", player.getName());
        namedParameterJdbcTemplate.update(
            INSERT_PLAYER_SESSION_MAPPING_QUERY,
            playerSessionMappingParams);
      }
    });
  }

  @Override
  public Optional<Session> findById(String sessionId) {
    List<Player> players = namedParameterJdbcTemplate
        .query(LIST_PLAYER_SESSION_MAPPINGS_QUERY,
            new MapSqlParameterSource().addValue("sessionid", sessionId),
            (resultSet, i) -> Player
                .builder()
                .name(resultSet.getString("name"))
                .creationDate(resultSet.getObject("creation_date", OffsetDateTime.class))
                .build());

    log.info("Players {} in session {}", players, sessionId);

    var moves = namedParameterJdbcTemplate
        .query(LIST_MOVES_QUERY, new MapSqlParameterSource().addValue("sessionid", sessionId),
            (resultSet, i) -> new Move(Type.of(resultSet.getString("type")),
                Player.builder().name(resultSet.getString("name")).build(), resultSet.getObject("creation_date", OffsetDateTime.class))
        );

    log.info("Moves {} in session {}", moves, sessionId);

    var sessions = namedParameterJdbcTemplate
        .query(LIST_SESSIONS_QUERY, new MapSqlParameterSource().addValue("sessionid", sessionId), (resultSet, i) -> {
              var winner = resultSet.getString("winner");
              return Session
                  .builder()
                  .id(resultSet.getString("session_id"))
                  .players(new HashSet<>(players))
                  .moves(new HashSet<>(moves))
                  .winner(Optional.ofNullable(resultSet.getString("winner"))
                      .map(w -> Player.builder().name(winner).build())
                      .orElse(null))
                  .build();
            }
        );

    return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions.get(0));
  }
}
