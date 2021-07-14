package com.rps.app.adapters.postgres;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class SqlQueries {

  public static final String INSERT_SESSION_QUERY = "INSERT INTO sessions (session_id, creation_date) VALUES (:sessionid, :creationdate)";
  public static final String UPDATE_SESSION_QUERY = "UPDATE sessions SET winner = :winner WHERE session_id = :sessionid";
  public static final String INSERT_PLAYER_SESSION_MAPPING_QUERY = "INSERT INTO player_session_mapping (session_id, player_id) VALUES "
      + "(:sessionid, (SELECT id FROM players where name = :playername))";
  public static final String LIST_SESSIONS_QUERY = "SELECT session_id, creation_date, winner FROM sessions WHERE session_id = :sessionid";
  public static final String LIST_PLAYER_SESSION_MAPPINGS_QUERY = "SELECT p.name, p.creation_date FROM players p left outer join player_session_mapping psm on  p.id = psm.player_id where psm.session_id = :sessionid";
  public static final String INSERT_MOVE_QUERY = "INSERT INTO moves (session_id, player_id, type, creation_date) VALUES (:sessionid, (SELECT id FROM players where name = :playername), :type, :playedAt)";
  public static final String LIST_MOVES_QUERY = "SELECT m.session_id, p.name, m.type, m.creation_date FROM moves m left outer join players p ON p.id = m.player_id WHERE session_id = :sessionid";
}
