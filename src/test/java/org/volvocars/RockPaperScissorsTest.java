package org.volvocars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RockPaperScissorsTest {
  private final RockPaperScissors rockPaperScissors = new RockPaperScissors();

  String rock = "A";
  String paper = "B";
  String scissors = "C";

  @Test
  void rockBeatsScissors() {
    String result = rockPaperScissors.play("A", "B");
    assertEquals("A", result);
  }

  @Test
  @ParameterizedTest
  @CsvSource({
    "B,A,A", "A,C,C", "C,A,C",
  })
  void manyTests(String player1, String player2, String expectedResult) {
    String result = rockPaperScissors.play(player1, player2);
    assertEquals(expectedResult, result);
  }

}
