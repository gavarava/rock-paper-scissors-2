package com.rps.app.core.metrics;


import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisteredPlayersCounter {

  private final MeterRegistry meterRegistry;

  public void increment() {
    meterRegistry.counter("registered_players").increment();
  }
}
