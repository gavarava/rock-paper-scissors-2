package com.rps.app.core.services;

import com.rps.app.core.model.Game;
import com.rps.app.core.model.Move;
import com.rps.app.core.model.Player;

public interface RockPaperScissorsService {

  Game start(Player player);

  Game join(Player player, String game);

  Game play(String game, Move move);

  Game result(String game);
}
