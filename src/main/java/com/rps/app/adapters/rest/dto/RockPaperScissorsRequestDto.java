package com.rps.app.adapters.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@AllArgsConstructor
@Builder
@JsonDeserialize(builder = RockPaperScissorsRequestDto.RockPaperScissorsRequestDtoBuilder.class)
public class RockPaperScissorsRequestDto {


  @NotNull
  @JsonProperty("player")
  String player;
  @JsonProperty("gameId")
  Long gameId;
  @JsonProperty("move")
  String move;
}
