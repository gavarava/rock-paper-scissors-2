package com.rps.app.core.services;

import com.rps.app.core.model.Game;
import com.rps.app.core.model.Move;
import com.rps.app.core.model.Player;
import java.util.Optional;

public interface RockPaperScissorsGameplay {

  Optional<Game> start(Player player);

  Optional<Game> join(Player player, Long game);

  void play(Player player, Move move, Long game);

  Optional<Game> result(Long game);
}
