package com.rps.app.core.services;

import static com.rps.app.core.model.Move.Type.PAPER;
import static com.rps.app.core.model.Move.Type.ROCK;
import static com.rps.app.core.model.Move.Type.of;
import static java.time.OffsetDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.google.common.collect.Sets;
import com.rps.app.adapters.memory.TransientSessionsRepository;
import com.rps.app.core.metrics.StartedSessionsCounter;
import com.rps.app.core.model.Session;
import com.rps.app.core.model.Move;
import com.rps.app.core.model.Player;
import com.rps.app.ports.SessionsRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.shaded.com.google.common.collect.Maps;

class DefaultRockPaperScissorsServiceTest {

  private SessionsRepository sessionsRepository;
  @Mock
  private StartedSessionsCounter startedSessionsCounter;
  private DefaultRockPaperScissorsService rockPaperScissorsService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    sessionsRepository = new TransientSessionsRepository(Maps.newHashMap());
    doNothing().when(startedSessionsCounter).increment();
    rockPaperScissorsService = new DefaultRockPaperScissorsService(sessionsRepository, startedSessionsCounter);
  }

  @Test
  void shouldBeAbleToJoinSession_whenInviteExists() {
    var player1 = Player.builder().name("Player1").build();
    var session = Session.builder()
        .players(Sets.newHashSet(player1))
        .id(UUID.randomUUID().toString())
        //.moves(Sets.newHashSet(new Move(ROCK, player1, now())))
        .build();
    session = sessionsRepository.create(session);

    var player2 = Player.builder().name("Player2").build();
    var result = rockPaperScissorsService.join(player2, session.getId());

    assertThat(result.getPlayers()).hasSize(2);
  }

  @Test
  void shouldStartSessionAndIncrementStartedSessionsCounter_whenStartingSession() {
    var player1 = Player.builder().name("Player1").creationDate(OffsetDateTime.now()).build();
    var startedSession = rockPaperScissorsService.start(player1);
    assertThat(startedSession.getPlayers()).hasSize(1);
    verify(startedSessionsCounter).increment();
  }

  @Test
  void shouldUpdateMove_whenSessionHasBeenStarted() {
    var player1 = Player.builder().name("Player1").creationDate(OffsetDateTime.now()).build();
    var startedSession = rockPaperScissorsService.start(player1);
    assertThat(startedSession.getPlayers()).hasSize(1);

    var player1FirstMove = new Move(of("ROCK"), player1, now());
    var result = rockPaperScissorsService.play(startedSession.getId(), player1FirstMove);

    assertThat(result.getMoves()).hasSize(1);
    assertThat(result.getLatestMove()).isEqualTo(Optional.of(player1FirstMove));
  }

  @Test
  void shouldUpdateWinner_whenOnePlayerWins() {
    var player1 = Player.builder().name("Player1").creationDate(OffsetDateTime.now()).build();
    var session = Session.builder()
        .id(UUID.randomUUID().toString())
        .players(Sets.newHashSet(player1)).build();
    session = sessionsRepository.create(session);

    var player1FirstMove = new Move(PAPER, player1, now());
    rockPaperScissorsService.play(session.getId(), player1FirstMove);

    var player2 = Player.builder().name("Player2").creationDate(OffsetDateTime.now()).build();
    rockPaperScissorsService.join(player2, session.getId());
    var player2FirstMove = new Move(ROCK, player2, now());
    var result = rockPaperScissorsService.play(session.getId(), player2FirstMove);

    assertThat(result.getWinner()).isEqualTo(player1);
  }

  @Test
  void shouldReturnSessionWithWinner_whenCheckingResult() {
    var player1 = Player.builder().name("Player1").creationDate(OffsetDateTime.now()).build();
    var session = Session.builder()
        .id(UUID.randomUUID().toString())
        .players(Sets.newHashSet(player1)).moves(Sets.newHashSet(new Move(ROCK, player1, now()))).build();
    var player2 = Player.builder().name("Player2").creationDate(OffsetDateTime.now()).build();
    session = sessionsRepository.create(session);

    rockPaperScissorsService.join(player2, session.getId());
    rockPaperScissorsService.play(session.getId(), new Move(PAPER, player2, now()));

    var result = rockPaperScissorsService.result(session.getId());

    assertThat(result.getWinner()).isEqualTo(player2);
  }
}