package com.rps.app.core.services;

import static com.rps.app.core.model.Move.Type.PAPER;
import static com.rps.app.core.model.Move.Type.ROCK;
import static java.time.OffsetDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.google.common.collect.Sets;
import com.rps.app.adapters.memory.TransientGameRepository;
import com.rps.app.core.model.Game;
import com.rps.app.core.model.Move;
import com.rps.app.core.model.Player;
import com.rps.app.ports.GameRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.shaded.com.google.common.collect.Maps;

class DefaultRockPaperScissorsServiceTest {

  private GameRepository gameRepository;
  private DefaultRockPaperScissorsService rockPaperScissorsService;

  @BeforeEach
  void setup() {
    gameRepository = new TransientGameRepository(Maps.newHashMap());
    rockPaperScissorsService = new DefaultRockPaperScissorsService(gameRepository);
  }

  @Test
  void shouldBeAbleToJoinGame_whenInviteExists() {
    var player1 = Player.builder().name("Player1").build();
    var game = Game.builder()
        .players(Sets.newHashSet(player1))
        //.moves(Sets.newHashSet(new Move(ROCK, player1, now())))
        .build();
    game = gameRepository.create(game);

    var player2 = Player.builder().name("Player2").build();
    var result = rockPaperScissorsService.join(player2, game.getId());

    assertThat(result.getPlayers()).hasSize(2);
  }

  @Test
  void shouldUpdateWinner_whenOnePlayerWins() {
    var player1 = Player.builder().name("Player1").creationDate(OffsetDateTime.now()).build();
    var game = Game.builder().players(Sets.newHashSet(player1)).moves(Sets.newHashSet(new Move(ROCK, player1, now()))).build();
    game = gameRepository.create(game);

    var player2 = Player.builder().name("Player2").creationDate(OffsetDateTime.now()).build();
    rockPaperScissorsService.join(player2, game.getId());
    var result = rockPaperScissorsService.play(game.getId(), new Move(PAPER, player2, now()));

    assertThat(result.getWinner()).isEqualTo(player2);
  }

  @Test
  void shouldReturnGameWithWinner_whenCheckingResult() {
    var player1 = Player.builder().name("Player1").creationDate(OffsetDateTime.now()).build();
    var game = Game.builder().players(Sets.newHashSet(player1)).moves(Sets.newHashSet(new Move(ROCK, player1, now()))).build();
    var player2 = Player.builder().name("Player2").creationDate(OffsetDateTime.now()).build();
    game = gameRepository.create(game);

    rockPaperScissorsService.join(player2, game.getId());
    rockPaperScissorsService.play(game.getId(), new Move(PAPER, player2, now()));

    var result = rockPaperScissorsService.result(game.getId());

    assertThat(result.getWinner()).isEqualTo(player2);
  }
}