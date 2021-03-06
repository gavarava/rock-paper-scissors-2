package com.rps.app.core.model;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Session {

  @NonNull
  String id;
  State state;

  @NonNull
  Set<Player> players;
  Set<Move> moves;
  Player winner;

  public Optional<Move> getLatestMove() {
    return moves == null ? Optional.empty()
        : moves.stream().min(Comparator.comparing(Move::getPlayedAt));
  }
}
