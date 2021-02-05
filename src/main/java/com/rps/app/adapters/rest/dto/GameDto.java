package com.rps.app.adapters.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rps.app.core.model.Game;
import lombok.Builder;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@Builder
public class GameDto {

  Long id;
  String winner;

  public static GameDto fromDomain(Game game) {
    return GameDto.builder()
        .id(game.getId())
        //.state(game.getState().name())
        .winner(game.getWinner() != null ? game.getWinner().getName() : null)
        .build();
  }

}
