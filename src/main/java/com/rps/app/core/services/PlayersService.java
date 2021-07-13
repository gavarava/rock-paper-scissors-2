package com.rps.app.core.services;

import com.rps.app.core.metrics.RegisteredPlayersCounter;
import com.rps.app.core.model.Player;
import com.rps.app.ports.PlayersRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("prod")
@AllArgsConstructor
public class PlayersService {

  private final PlayersRepository playersRepository;
  private final RegisteredPlayersCounter registeredPlayersCounter;

  public Player createPlayer(String name) {
    Player player = Player
        .builder()
        .name(name)
        .build();
    log.info("Created player with name {}", name);
    playersRepository.create(player);
    registeredPlayersCounter.increment();
    return player;
  }

  public Optional<Player> getPlayer(String name) {
    log.info("Getting player with name {}", name);
    return Optional.ofNullable(playersRepository.findByName(name));
  }

  public Player updatePlayer(Player player) {
    log.info("Updating player with name {}", player.getName());
    playersRepository.update(player);
    return player;
  }
}
