package com.rps.app.core.model;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Player {

  OffsetDateTime creationDate;
  String name;
}
