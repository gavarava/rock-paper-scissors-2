package com.rps.app.core.model;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class Player {

  OffsetDateTime creationDate;
  Long gamesPlayed;
  @NonNull
  String name;
}
