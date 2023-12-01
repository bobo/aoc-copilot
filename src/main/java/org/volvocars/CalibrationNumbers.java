package org.volvocars;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalibrationNumbers {

  private final WordSanitizer wordSanitizer;

  public CalibrationNumbers(WordSanitizer wordSanitizer) {
    this.wordSanitizer = wordSanitizer;
  }

  private static final Map<String, String> NUMBER_WORDS =
      Map.of(
          "one", "1",
          "two", "2",
          "three", "3",
          "four", "4",
          "five", "5",
          "six", "6",
          "seven", "7",
          "eight", "8",
          "nine", "9");

  public String onlyNumbers(String input) {
    if (input.equals("zero")) {
      return "";
    }
    String result = input;
    Pattern pattern = Pattern.compile(String.join("|", NUMBER_WORDS.keySet()));
    Matcher matcher = pattern.matcher(result);
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      matcher.appendReplacement(sb, NUMBER_WORDS.get(matcher.group()));
    }
    matcher.appendTail(sb);
    result = sb.toString();
    return result.replaceAll("\\D", "");
  }

  public String firstAndLast(String input) {
    String onlyNumbers = onlyNumbers(input);
    if (onlyNumbers.length() == 1) {
      return onlyNumbers + onlyNumbers;
    } else if (onlyNumbers.length() >= 2) {
      return onlyNumbers.charAt(0) + String.valueOf(onlyNumbers.charAt(onlyNumbers.length() - 1));
    } else {
      return "";
    }
  }

  public int firstAndLastNumberFromString(String input) {
    String onlyNumbers = onlyNumbers(input);
    String firstAndLast = firstAndLast(onlyNumbers);
    return Integer.parseInt(firstAndLast);
  }

  public int sumNumbers(List<Integer> numbers) {
    return numbers.stream().mapToInt(Integer::intValue).sum();
  }

  public int createResult(String input) {
    String[] lines = input.split("\n");
    int sum = 0;
    for (String line : lines) {
      String sanitizedLine = wordSanitizer.sanitize(line);
      sum += firstAndLastNumberFromString(sanitizedLine);
    }
    return sum;
  }
}
