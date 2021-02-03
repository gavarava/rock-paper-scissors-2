package com.rps.app.adapters.rest.dto;

import lombok.Value;

@Value
public class RockPaperScissorsRequestDto {

  String playerName;
  Long gameId;
  String move;
}
