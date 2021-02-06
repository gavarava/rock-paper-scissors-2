package com.rps.app.core.services;

import static com.rps.app.core.model.Move.Type.PAPER;
import static com.rps.app.core.model.Move.Type.ROCK;
import static java.time.OffsetDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.google.common.collect.Sets;
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

class DefaultRockPaperScissorsServiceTest {

  private static final long GAME_ID = 1234567L;
  @Mock
  private GameRepository gameRepository;

  private DefaultRockPaperScissorsService rockPaperScissorsService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    rockPaperScissorsService = new DefaultRockPaperScissorsService(gameRepository);
  }

  @Test
  void shouldBeAbleToJoinGame_whenInviteExists() {
    var player1 = Player.builder().name("Player1").build();
    var game = Game.builder().id(GAME_ID).players(Sets.newHashSet(player1)).moves(Sets.newHashSet(new Move(ROCK, player1, now()))).build();
    var player2 = Player.builder().name("Player2").build();
    when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(game));
    when(gameRepository.update(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

    var result = rockPaperScissorsService.join(player2, GAME_ID);

    assertThat(result.getPlayers()).hasSize(2);
    assertThat(result.getMoves()).hasSize(1);
  }

  @Test
  void shouldUpdateWinner_whenOnePlayerWins() {
    var player1 = Player.builder().name("Player1").creationDate(OffsetDateTime.now()).build();
    var game = Game.builder().id(GAME_ID).players(Sets.newHashSet(player1)).moves(Sets.newHashSet(new Move(ROCK, player1, now()))).build();
    var player2 = Player.builder().name("Player2").creationDate(OffsetDateTime.now()).build();
    when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(game));
    // thenAnswer can be used to return with what came as an argument, so its always latest updated
    when(gameRepository.update(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

    rockPaperScissorsService.join(player2, GAME_ID);
    var result = rockPaperScissorsService.play(GAME_ID, new Move(PAPER, player2, now()));

    assertThat(result.getWinner()).isEqualTo(player2);
  }
}