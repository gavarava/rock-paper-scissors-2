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
  //FIXME name should be unique in the database
  String name;
}
