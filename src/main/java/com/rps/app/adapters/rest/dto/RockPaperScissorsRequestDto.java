package com.rps.app.adapters.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@AllArgsConstructor
public class RockPaperScissorsRequestDto {

  String player;
  Long gameId;
  String move;
}
