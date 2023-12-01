package org.volvocars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class WordSanitizerTest {
  WordSanitizer sanitize = new WordSanitizer();

  @ParameterizedTest
  @CsvSource({
          "oneight,oneeight",
          "eightwo,eighttwo",
          "twone,twoone",
          "eighthree,eightthree",
          "nineight,nineeight",
          "threeight,threeeight",
          "sevenine,sevennine",
          "fiveight,fiveeight",

  })
  void appendsExtraLetterWhenUsedByTwoNumbers(String input, String expectedResult) {
    String result = sanitize.sanitize(input);
    assertEquals(expectedResult, result);
  }

  @Test
  void canBeInPartOfString() {
    String result = sanitize.sanitize("1oneight8");
    assertEquals("1oneeight8", result);
  }
}
