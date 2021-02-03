package com.rps.app.ports;

import com.rps.app.core.model.Game;

public interface GameRepository {

  Game create(Game game);

  Game update(Game game);

  Game findById(Long gameId);
}
