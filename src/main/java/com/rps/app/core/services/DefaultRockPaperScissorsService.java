package com.rps.app.core.services;

import static com.rps.app.core.model.Move.Type.PAPER;
import static com.rps.app.core.model.Move.Type.ROCK;
import static com.rps.app.core.model.Move.Type.SCISSORS;

import com.rps.app.core.model.Game;
import com.rps.app.core.model.Move;
import com.rps.app.core.model.Player;
import com.rps.app.core.model.State;
import com.rps.app.ports.GameRepository;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.var;

@AllArgsConstructor
public class DefaultRockPaperScissorsService implements RockPaperScissorsService {

  private final GameRepository gameRepository;

  @Override
  public Game start(Player player) {
    return gameRepository.create(Game.builder().players(Set.of(player)).state(State.START).build());
  }

  @Override
  public Game join(Player player, Long gameId) {
    return gameRepository.findById(gameId).stream()
        .map(game -> {
          game.getPlayers().add(player);
          return gameRepository.update(game);
        })
        .findFirst()
        // TODO Add Exception Handling
        .get();
  }

  @Override
  public Game play(Long gameId, Move move) {
    return gameRepository.findById(gameId).stream()
        .map(game -> {
          var lastPlayedMoveOptional = game.getLatestMove();
          Set<Move> moves = game.getMoves();
          if (lastPlayedMoveOptional.isPresent()) {
            Move lastPlayedMove = lastPlayedMoveOptional.get();
            moves.add(lastPlayedMove);
            game = game.toBuilder()
                .moves(moves)
                .winner(evaluateWinner(move, lastPlayedMove)).build();
          } else {
            game = game.toBuilder()
                .moves(moves)
                .build();
          }
          return gameRepository.update(game);
        })
        .findFirst()
        // TODO Add Exception Handling
        .get();
  }

  private Player evaluateWinner(Move currentMove, Move lastPlayedMove) {
    switch (currentMove.getType()) {
      case ROCK:
        if (lastPlayedMove.getType() == PAPER) {
          return lastPlayedMove.getPlayer();
        } else {
          return currentMove.getPlayer();
        }
      case PAPER:
        if (lastPlayedMove.getType() == SCISSORS) {
          return lastPlayedMove.getPlayer();
        } else {
          return currentMove.getPlayer();
        }
      case SCISSORS:
        if (lastPlayedMove.getType() == ROCK) {
          return lastPlayedMove.getPlayer();
        } else {
          return currentMove.getPlayer();
        }
      default:
        throw new IllegalArgumentException(
            String.format("Invalid Move by player %s %s", currentMove.getPlayer().getName(), currentMove.getType().name()));
    }
  }

  @Override
  public Game result(Long gameId) {
    // TODO Add Exception Handling
    return gameRepository.findById(gameId).get();
  }
}
