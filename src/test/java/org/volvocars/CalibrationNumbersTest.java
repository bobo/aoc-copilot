package org.volvocars;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalibrationNumbersTest {
  WordSanitizer wordSanitizer = new WordSanitizer();
  CalibrationNumbers calibrationNumbers = new CalibrationNumbers(wordSanitizer);

  @ParameterizedTest
  @CsvSource({
    "abc123,123",
    "abc123abc,123",
    "1tre2tre3,123",
    "oneone,11",
    "1two2three,1223",
    "fourfivesix7nio,4567",
    "sevenhf2,72",
    "5phctbfzjttxbmtqxhbhdjzlsbtdqhjcsqhp4onedpmvsqqxhh,541",
    "ninem1,91",
    "oneight,18"
  })
  void canFindOnlyNumbers(String everything, String expectedResult) {
    String result = calibrationNumbers.onlyNumbers(wordSanitizer.sanitize(everything));
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @CsvSource({
    "one,1", "two,2", "three,3", "four,4", "five,5", "six,6", "seven,7", "eight,8", "nine,9"
  })
  void spelledOutNumbersAreNumbers(String candidate, String expectedResult) {
    String result = calibrationNumbers.onlyNumbers(candidate);
    assertEquals(expectedResult, result);
  }



  @ParameterizedTest
  @CsvSource({
    "123,13",
    "12345,15",
    "666661,61",
    "44, 44",
  })
  void firstAndLastNumber(String candidate, String expectedResult) {
    String result = calibrationNumbers.firstAndLast(candidate);
    assertEquals(expectedResult, result);
  }

  @Test
  void firstAndLastIsTheSameIfOnlyOne() {
    String result = calibrationNumbers.firstAndLast("a1a");
    assertEquals("11", result);
  }

  @ParameterizedTest
  @CsvSource({
    "abc123,13",
    "abc123abc,13",
    "1tre2tre3,13",
    "two1nine,29",
    "eightwothree,83",
    "abcone2threexyz,13",
    "xtwone3four,24",
    "4nineeightseven2,42",
    "zoneight234,14",
    "7pqrstsixteen,76",
  })
  void canFindFirstAndLastNumberFromString(String candidate, int expectedResult) {
    int result = calibrationNumbers.firstAndLastNumberFromString(wordSanitizer.sanitize(candidate));
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @CsvSource({
    "two1nine,29",
    "eightwothree,83",
    "abcone2threexyz,13",
  })
  void canFindFirstAndLastNumberFromString2(String candidate, int expectedResult) {
    int result = calibrationNumbers.firstAndLastNumberFromString(candidate);
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @CsvSource({
    "two1nine,29",
    "eightwothree,83",
  })
  void canFindFirstAndLastNumberFromString3(String candidate, int expectedResult) {
    int result = calibrationNumbers.firstAndLastNumberFromString(candidate);
    assertEquals(expectedResult, result);
  }

  @Test
  void canSumNumbers() {
    List<Integer> numbers = List.of(1, 2, 3);
    int result = calibrationNumbers.sumNumbers(numbers);
    assertEquals(6, result);
  }

  @Test
  void canSumMoreNumbers() {
    List<Integer> numbers = List.of(29, 83, 13, 24, 42, 14, 76);
    int result = calibrationNumbers.sumNumbers(numbers);
    assertEquals(281, result);
  }

  @Test
  void canCreateResult() {
    String input = """
    1abc2
    pqr3stu8vwx
    a1b2c3d4e5f
    treb7uchet
  """;
    int result = calibrationNumbers.createResult(input);
    assertEquals(142, result);
  }

  @Test
  void canCreateResultWithNumbers() {
    String input =
        """
two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen
  """;
    int result = calibrationNumbers.createResult(input);
    assertEquals(281, result);
  }

  @Test
  void callsSanitizeInCreateResult() {
    MockedWordSanitizer mockedWordSanitizer = new MockedWordSanitizer();
    CalibrationNumbers calibrationNumbersWithMocked = new CalibrationNumbers(mockedWordSanitizer);
    int result = calibrationNumbersWithMocked.createResult("one");
    assertEquals(1, mockedWordSanitizer.calledCount);
    assertEquals(22, result);
  }
  @Test
  void callsSanitizeForEveryRowInCreateResult() {
    MockedWordSanitizer mockedWordSanitizer = new MockedWordSanitizer();
    CalibrationNumbers calibrationNumbersWithMocked = new CalibrationNumbers(mockedWordSanitizer);
    int result = calibrationNumbersWithMocked.createResult("""
one
two
""");
    assertEquals(2, mockedWordSanitizer.calledCount);
  }
  class MockedWordSanitizer extends WordSanitizer {
    private int calledCount = 0;
    @Override
    public String sanitize(String input) {
    calledCount++;
      return "two";
    }



  }


}
