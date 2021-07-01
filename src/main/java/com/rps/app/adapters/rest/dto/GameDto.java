package com.rps.app.adapters.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rps.app.core.model.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Value
@Builder
@JsonDeserialize(builder = GameDto.GameDtoBuilder.class)
public class GameDto {

  @JsonProperty("gameId")
  String id;
  @JsonProperty("winner")
  String winner;

  public static GameDto fromDomain(Game game) {
    return GameDto.builder()
        .id(game.getId())
        //.state(game.getState().name())
        .winner(game.getWinner() != null ? game.getWinner().getName() : null)
        .build();
  }

}
