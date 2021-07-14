package com.rps.app.adapters.postgres;

import static org.assertj.core.api.Assertions.assertThat;

import com.rps.app.core.model.Session;
import com.rps.app.core.model.Player;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Slf4j
class PostgresSessionsRepositoryIT extends RepositoryTestsBase {

  @Autowired
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  PostgresSessionsRepository sessionsRepository;

  @Autowired
  PostgresPlayersRepository playersRepository;

  @Test
  void shouldCreateGameWithPlayersMapped_whenCreate() {
    var player1 = Player.builder().name("PlayerX").build();
    playersRepository.create(player1);
    var player2 = Player.builder().name("PlayerY").build();
    playersRepository.create(player2);

    var session = Session.builder()
        .id(UUID.randomUUID().toString())
        .players(Set.of(player1, player2))
        .build();

    sessionsRepository.create(session);

    var result = sessionsRepository.findById(session.getId());
    assertThat(result).hasValueSatisfying(game -> {
      assertThat(game.getId()).isEqualTo(session.getId());
      assertThat(game.getPlayers()).hasSize(2);
      assertThat(game.getPlayers())
          .anyMatch(player -> player.getName().equals(player1.getName()) || player.getName().equals(player2.getName()));
    });
  }

  @Test
  void shouldUpdatePlayersToSessionsMapping_whenNewPlayersAdded() {
    var player1 = Player.builder().name("PlayerX").build();
    playersRepository.create(player1);

    var session = Session.builder()
        .id(UUID.randomUUID().toString())
        .players(Set.of(player1))
        .build();
    sessionsRepository.create(session);

    var player2 = Player.builder().name("PlayerY").build();
    playersRepository.create(player2);
    var twoPlayers = Set.of(player1, player2);
    var sessionWithTwoPlayers = session.toBuilder()
        .players(twoPlayers)
        .build();

    var updatedSessionWithTwoPlayers = sessionsRepository.update(sessionWithTwoPlayers);
    assertThat(updatedSessionWithTwoPlayers.getPlayers()).hasSize(2);
    assertThat(getActualPlayerNames(updatedSessionWithTwoPlayers))
        .containsExactlyInAnyOrder(player1.getName(), player2.getName());
  }

  private List<String> getActualPlayerNames(Session updatedSessionWithTwoPlayers) {
    return updatedSessionWithTwoPlayers.getPlayers().stream().map(Player::getName).collect(Collectors.toList());
  }
}
