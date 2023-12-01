package org.volvocars;

import java.util.Map;

public class WordSanitizer {
  private static final Map<String, String> REPLACEMENTS = Map.of(
          "oneight", "oneeight",
          "eightwo", "eighttwo",
          "twone", "twoone",
          "eighthree", "eightthree",
          "nineight", "nineeight",
          "threeight", "threeeight",
          "sevenine", "sevennine",
          "fiveight", "fiveeight"
  );

  public String sanitize(String input) {
    String result = input;
    for (Map.Entry<String, String> entry : REPLACEMENTS.entrySet()) {
      result = result.replace(entry.getKey(), entry.getValue());
    }
    return result;
  }
}
