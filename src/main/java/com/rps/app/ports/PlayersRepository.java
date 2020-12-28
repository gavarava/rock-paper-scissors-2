package com.rps.app.ports;

import com.rps.app.core.model.Player;

public interface PlayersRepository {

  void create(Player player);

  Player findByName(String playerName);
}
