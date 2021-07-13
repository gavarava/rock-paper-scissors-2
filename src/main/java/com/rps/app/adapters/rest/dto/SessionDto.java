package com.rps.app.adapters.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rps.app.core.model.Session;
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
  @JsonProperty("winner")
  String winner;

  public static SessionDto fromDomain(Session session) {
    return SessionDto.builder()
        .id(session.getId())
        //.state(game.getState().name())
        .winner(session.getWinner() != null ? session.getWinner().getName() : null)
        .build();
  }

}
