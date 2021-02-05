package com.rps.app.adapters.memory;

import com.rps.app.core.model.Game;
import com.rps.app.ports.GameRepository;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Slf4j
@AllArgsConstructor
public class TransientGameRepository implements GameRepository {

  Map<Long, Game> gameMap;

  @Override
  public Game create(Game game) {
    var gameId = 123456789L; //System.currentTimeMillis();
    game = game.toBuilder().id(gameId).build();
    gameMap.put(gameId, game);
    return gameMap.get(gameId);
  }

  @Override
  public Game update(Game game) {
    var gameId = game.getId();
    var gameOptional = findById(gameId);
    if (gameOptional.isPresent()) {
      gameMap.put(gameId, game);
    }
    return gameMap.get(gameId);
  }

  @Override
  public Optional<Game> findById(Long gameId) {
    return Optional.ofNullable(gameMap.get(gameId));
  }
}
