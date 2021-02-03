package com.rps.app.core.model;

import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Game {

  Long id;
  State state;
  Set<Player> players;
}
