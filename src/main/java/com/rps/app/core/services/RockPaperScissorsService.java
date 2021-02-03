package com.rps.app.core.services;

import com.rps.app.core.model.Move;
import com.rps.app.core.model.Player;
import com.rps.app.core.model.Game;
import com.rps.app.core.model.State;
import com.rps.app.ports.GameRepository;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.var;

@AllArgsConstructor
public class RockPaperScissorsService implements RockPaperScissorsGameplay {

  private final GameRepository gameRepository;

  @Override
  public Optional<Game> start(Player player) {
    return Optional.of(gameRepository.create(Game.builder().players(Set.of(player)).state(State.START).build()));
  }

  @Override
  public Optional<Game> join(Player player, Long gameId) {
    var game = gameRepository.findById(gameId);
    game.getPlayers().add(player);
    return Optional.of(gameRepository.update(game.toBuilder().state(State.INPROGRESS).build()));
  }

  @Override
  public void play(Player player, Move move, Long gameId) {
    var game = gameRepository.findById(gameId);
    game.getPlayers().add(player);
  }

  @Override
  public Optional<Game> result(Long game) {
    return Optional.empty();
  }
}
