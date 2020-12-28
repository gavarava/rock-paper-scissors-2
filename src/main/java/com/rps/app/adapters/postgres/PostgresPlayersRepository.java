package com.rps.app.adapters.postgres;

import com.rps.app.core.model.Player;
import com.rps.app.ports.PlayersRepository;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@AllArgsConstructor
@Slf4j
public class PostgresPlayersRepository implements PlayersRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public void create(Player player) {
    var params = new MapSqlParameterSource()
        .addValue("name", player.getName())
        .addValue("creationdate", player.getCreationDate(), Types.TIMESTAMP_WITH_TIMEZONE);
    int updatedRows = namedParameterJdbcTemplate.update("INSERT INTO players (name, creationdate) VALUES (:name, :creationdate)", params);
    log.info("updatedRows = {}", updatedRows);
  }

  @Override
  public Player findByName(String playerName) {
    List<Player> players = namedParameterJdbcTemplate
        .query("SELECT name, creationdate FROM players WHERE name = :name",
            new MapSqlParameterSource().addValue("name", playerName),
            (resultSet, i) -> Player
                .builder()
                .name(resultSet.getString("name"))
                .creationDate(resultSet.getObject("creationdate", OffsetDateTime.class))
                .build());
    return players.get(0);
  }
}
