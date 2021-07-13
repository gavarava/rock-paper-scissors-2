package com.rps.app.adapters.memory;

import com.rps.app.core.model.Session;
import com.rps.app.ports.SessionsRepository;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Slf4j
@AllArgsConstructor
public class TransientSessionsRepository implements SessionsRepository {

  Map<String, Session> gameMap;

  @Override
  public Session create(Session session) {
    var gameId = UUID.randomUUID().toString();
    session = session.toBuilder().id(gameId).build();
    gameMap.put(gameId, session);
    return gameMap.get(gameId);
  }

  @Override
  public Session update(Session session) {
    var gameId = session.getId();
    var gameOptional = findById(gameId);
    if (gameOptional.isPresent()) {
      gameMap.put(gameId, session);
    }
    return gameMap.get(gameId);
  }

  @Override
  public Optional<Session> findById(String gameId) {
    return Optional.ofNullable(gameMap.get(gameId));
  }
}
