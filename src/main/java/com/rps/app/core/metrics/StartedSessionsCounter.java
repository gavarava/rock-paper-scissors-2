package com.rps.app.core.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartedSessionsCounter {

  private final MeterRegistry meterRegistry;

  public void increment() {
    meterRegistry.counter("started_sessions").increment();
  }
}
