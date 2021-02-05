package com.rps.app.core.model;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Game {

  Long id;
  State state;
  Set<Player> players;
  Set<Move> moves;
  Player winner;

  public Optional<Move> getLatestMove() {
    return moves.stream().sorted(Comparator.comparing(Move::getPlayedAt)).findFirst();
  }
}
