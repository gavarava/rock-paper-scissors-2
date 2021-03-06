package com.rps.app.core.services;

import static com.rps.app.core.model.Move.Type.PAPER;
import static com.rps.app.core.model.Move.Type.ROCK;
import static com.rps.app.core.model.Move.Type.SCISSORS;

import com.google.common.collect.Sets;
import com.rps.app.core.metrics.StartedSessionsCounter;
import com.rps.app.core.model.Session;
import com.rps.app.core.model.Move;
import com.rps.app.core.model.Player;
import com.rps.app.core.model.State;
import com.rps.app.ports.SessionsRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DefaultRockPaperScissorsService implements RockPaperScissorsService {

  private final SessionsRepository sessionsRepository;
  private final StartedSessionsCounter startedSessionsCounter;

  @Override
  public Session start(Player player) {
    var game = sessionsRepository.create(Session.builder()
        .id(UUID.randomUUID().toString())
        .players(Sets.newHashSet(player)).state(State.START).build());
    startedSessionsCounter.increment();
    return game;
  }

  @Override
  public Session join(Player player, String gameId) {
    return sessionsRepository.findById(gameId).stream()
        .map(game -> {
          game.getPlayers().add(player);
          return sessionsRepository.update(game);
        })
        .findFirst()
        // TODO Add Exception Handling
        .get();
  }

  @Override
  public Session play(String gameId, Move move) {
    return sessionsRepository.findById(gameId).stream()
        .map(game -> {
          if (game.getLatestMove().isPresent()) {
            game = game.toBuilder()
                .winner(evaluateWinner(move, game.getLatestMove().get()))
                .build();
            game.getMoves().add(move);
          } else {
            game = game.toBuilder()
                .moves(Sets.newHashSet(move))
                .build();
          }
          return sessionsRepository.update(game);
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
        } else if (lastPlayedMove.getType() == ROCK) {
          // FIXME TIE as a result
          return null;
        } else {
          return currentMove.getPlayer();
        }
      case PAPER:
        if (lastPlayedMove.getType() == SCISSORS) {
          return lastPlayedMove.getPlayer();
        } else if (lastPlayedMove.getType() == PAPER) {
          // FIXME TIE as a result
          return null;
        } else {
          return currentMove.getPlayer();
        }
      case SCISSORS:
        if (lastPlayedMove.getType() == ROCK) {
          return lastPlayedMove.getPlayer();
        } else if (lastPlayedMove.getType() == SCISSORS) {
          // FIXME TIE as a result
          return null;
        } else {
          return currentMove.getPlayer();
        }
      default:
        throw new IllegalArgumentException(
            String.format("Invalid Move by player %s %s", currentMove.getPlayer().getName(), currentMove.getType().name()));
    }
  }

  @Override
  public Session result(String gameId) {
    // TODO Add Exception Handling
    return sessionsRepository.findById(gameId).get();
  }
}
