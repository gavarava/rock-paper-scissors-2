package com.rps.app.adapters.postgres;

import com.rps.app.core.model.Player;
import com.rps.app.ports.PlayersRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@AllArgsConstructor
public class PostgresPlayersRepository implements PlayersRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public void create(Player player) {
    throw  new RuntimeException("NOT IMPLEMENTED");
  }

  @Override
  public Player findByName(String playerName) {
    return null;
  }
}
