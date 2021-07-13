package com.rps.app.adapters.postgres;

import static lombok.AccessLevel.*;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class SqlQueries {

  public static final String INSERT_SESSION_QUERY = "INSERT INTO sessions (session_id, creation_date) VALUES (:sessionid, :creationdate)";
  public static final String INSERT_PLAYER_SESSION_MAPPING_QUERY = "INSERT INTO player_session_mapping (session_id, player_id) VALUES "
      + "(:sessionid, (SELECT id FROM players where name = :playername))";
  // INSERT INTO moves
  // INSERT INTO session_moves_mapping
  public static final String LIST_SESSIONS_QUERY = "SELECT session_id, creation_date FROM sessions WHERE session_id = :sessionid";
  public static final String LIST_PLAYER_SESSION_MAPPINGS_QUERY = "SELECT p.name, p.creation_date FROM players p left outer join player_session_mapping psm on  p.id = psm.player_id where psm.session_id = :sessionid";

  // UPDATE

}
