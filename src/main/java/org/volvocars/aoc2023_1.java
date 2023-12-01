package org.volvocars;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class aoc2023_1 {

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Path.of("src/main/resources/2023/input_1.txt"));
    int result = new CalibrationNumbers(new WordSanitizer()).createResult(input);
    System.out.println(result);
  }
}
