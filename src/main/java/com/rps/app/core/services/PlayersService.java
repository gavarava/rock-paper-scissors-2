package com.rps.app.core.services;

import com.rps.app.core.model.Player;
import com.rps.app.ports.PlayersRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class PlayersService {

  private final PlayersRepository playersRepository;

  public Player createPlayer(String name) {
    Player player = Player.builder().name(name).build();
    log.info("Created player with name {}", name);
    playersRepository.create(player);
    return player;
  }

  public Optional<Player> getPlayer(String name) {
    log.info("Getting player with name {}", name);
    return Optional.ofNullable(playersRepository.findByName(name));
  }

}
