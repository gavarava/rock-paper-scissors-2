package com.rps.app.adapters.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rps.app.core.model.Player;
import com.rps.app.core.model.Session;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Value
@Builder
@JsonDeserialize(builder = SessionDto.SessionDtoBuilder.class)
public class SessionDto {

  @JsonProperty("gameId")
  String id;
  @JsonProperty("players")
  String players;
  @JsonProperty("winner")
  String winner;

  public static SessionDto fromDomain(Session session) {
    return SessionDto.builder()
        .id(session.getId())
        .players(session.getPlayers().stream().map(Player::getName).collect(Collectors.toList()).toString())
        .winner(session.getWinner() != null ? session.getWinner().getName() : null)
        .build();
  }

}
