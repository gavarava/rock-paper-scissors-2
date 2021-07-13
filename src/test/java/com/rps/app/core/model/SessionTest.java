package com.rps.app.core.model;

import static java.time.OffsetDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

import com.rps.app.core.model.Move.Type;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SessionTest {

  @Test
  void shouldAlwaysGiveLatestMove() {
    var player = Player.builder().name("Player1").build();
    var firstMove = new Move(Type.of("ROCK"), player, now());
    var secondMove = new Move(Type.of("SCISSORS"), player, now());
    var session = Session.builder().id("GameID").moves(Set.of(firstMove, secondMove)).players(Set.of(player)).build();
    assertThat(session.getLatestMove()).isNotEmpty();
    assertThat(session.getLatestMove()).hasValue(firstMove);
  }
}
