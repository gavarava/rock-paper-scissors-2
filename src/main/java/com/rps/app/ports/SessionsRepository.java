package com.rps.app.ports;

import com.rps.app.core.model.Game;
import java.util.Optional;

public interface SessionsRepository {

  Game create(Game game);

  Game update(Game game);

  Optional<Game> findById(String gameId);
}
