package com.rps.app.core.model;

import java.time.OffsetDateTime;
import java.util.Locale;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Move {

  @NonNull
  Type type;

  @NonNull
  Player player;

  @NonNull
  OffsetDateTime playedAt;

  boolean notEvaluated = false;

  public enum Type {
    ROCK,
    PAPER,
    SCISSORS;

    public static Type of(String value) {
      if (value == null) {
        return null;
      }
      return Type.valueOf(value.toUpperCase(Locale.ROOT));
    }
  }
}
