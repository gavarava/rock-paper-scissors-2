package com.rps.app.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.rps.app.core.model.Move.Type;
import lombok.var;
import org.junit.jupiter.api.Test;

class MoveTypeTest {

  @Test
  void test() {
    var moveTypeRock = "ROCK";
    assertThat(Type.of(moveTypeRock)).isEqualTo(Type.ROCK);
  }

}