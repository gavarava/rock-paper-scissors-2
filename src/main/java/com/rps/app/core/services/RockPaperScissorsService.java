package com.rps.app.core.services;

import com.rps.app.core.model.Session;
import com.rps.app.core.model.Move;
import com.rps.app.core.model.Player;

public interface RockPaperScissorsService {

  Session start(Player player);

  Session join(Player player, String game);

  Session play(String game, Move move);

  Session result(String game);
}
