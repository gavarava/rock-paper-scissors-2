package com.rps.app.adapters.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rps.app.core.model.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Value
@Builder
@JsonDeserialize(builder = PlayerDto.PlayerDtoBuilder.class)
public class PlayerDto {

  @JsonProperty("name")
  String name;
  @JsonProperty("gamesPlayed")
  Long gamesPlayed;

  public Player toDomain() {
    return Player
        .builder()
        .name(name)
        .gamesPlayed(gamesPlayed)
        .build();
  }
}
