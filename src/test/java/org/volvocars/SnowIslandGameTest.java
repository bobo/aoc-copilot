package org.volvocars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SnowIslandGameTest {

  @ParameterizedTest
  @CsvSource(
      value = {
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green, 3 red, 1 blue|1",
        "Game 2: 3 blue, 4 red;|2",
        "Game 3: 3 blue|3"
      },
      delimiter = '|')
  void testParseGameNumber(String input, int expectedResult) {
    int result = SnowIslandGame.parseGameNumber(input);
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @CsvSource(
      value = {
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green, 3 red, 1 blue|3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green, 3 red, 1 blue",
        "Game 2: 3 blue, 4 red|3 blue, 4 red",
        "Game 3: 3 blue|3 blue"
      },
      delimiter = '|')
  void testParseGame(String input, String expectedResult) {
    String result = SnowIslandGame.parseGame(input);
    assertEquals(expectedResult, result);
  }

  @Test
  void testParseReveals() {
    String input = "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green";
    List<String> result = SnowIslandGame.parseReveals(input);
    assertEquals(3, result.size());
    assertEquals("3 blue, 4 red", result.get(0));
    assertEquals("1 red, 2 green, 6 blue", result.get(1));
    assertEquals("2 green", result.get(2));
  }

  @Test
  void testParseReveal() {
    String input = "3 blue, 4 red, 10 green";
    List<String> result = SnowIslandGame.parseReveal(input);
    assertEquals(3, result.size());
    assertEquals("3 blue", result.get(0));
    assertEquals("4 red", result.get(1));
    assertEquals("10 green", result.get(2));
  }

  @Test
  void testConvertRevealToMap() {
    List<String> input = List.of("3 blue", "4 red", "10 green");
    Map<String, Integer> result = SnowIslandGame.convertRevealToMap(input);
    assertEquals(3, result.size());
    assertEquals(3, result.get("blue"));
    assertEquals(4, result.get("red"));
    assertEquals(10, result.get("green"));
  }

  @Test
  void tesRevealPossible() {
    Map<String, Integer> inBag = Map.of("blue", 4);
    boolean result = SnowIslandGame.isRevealPossible(Map.of("blue", 3), inBag);
    assertTrue(result);
    result = SnowIslandGame.isRevealPossible(Map.of("green", 3), inBag);
    assertFalse(result);
    result = SnowIslandGame.isRevealPossible(Map.of("blue", 5), inBag);
    assertFalse(result);
  }

  @Test
  void testRevealPossible2() {
    Map<String, Integer> inBag = Map.of("blue", 1, "green", 2);
    boolean result = SnowIslandGame.isRevealPossible(Map.of("blue", 1), inBag);
    assertTrue(result);
    result = SnowIslandGame.isRevealPossible(Map.of("green", 2), inBag);
    assertTrue(result);
    result = SnowIslandGame.isRevealPossible(Map.of("blue", 1, "green", 3), inBag);
    assertFalse(result);
  }

  @Test
  void testRevealPossible3() {
    Map<String, Integer> inBag = Map.of("red", 12, "green", 13, "blue", 14);
    boolean result =
        SnowIslandGame.isRevealPossible(Map.of("green", 8, "blue", 6, "red", 20), inBag);
    assertFalse(result);
  }

  @Test
  void testConvertGameToReveals() {
    String input = "Game 3: 8 green, 6 blue, 20 red; 1 green, 1 blue";
    List<Map<String, Integer>> reveals = SnowIslandGame.convertGameToReveals(input);
    assertEquals(2, reveals.size());
    assertEquals(3, reveals.get(0).size());
    assertEquals(8, reveals.get(0).get("green"));
  }

  @Test
  void testGameIsPossibleIfAllRevealsArePossible() {
    List<Map<String, Integer>> possibleReveals = List.of(Map.of("green", 8), Map.of("green", 1));
    List<Map<String, Integer>> imPossibleReveals = List.of(Map.of("green", 8), Map.of("green", 10));
    Map<String, Integer> bag = Map.of("green", 9);
    assertTrue(SnowIslandGame.isGamePossible(possibleReveals, bag));
    assertFalse(SnowIslandGame.isGamePossible(imPossibleReveals, bag));
  }

  @Test
  void findPossibleGamesWithSingleColor() {
    String input = """
Game 1: 1 green; 2 green
Game 2: 2 green; 8 green
Game 3: 8 green
""";
    Map<String, Integer> bag = Map.of("green", 3);
    List<Integer> result = SnowIslandGame.findPossibleGames(input, bag);
    assertEquals(1, result.size());
    assertEquals(1, result.get(0));
  }

  @Test
  void findPossibleGameWhenNoGameIsPossible() {
    String input = """
Game 3: 8 green, 6 blue, 20 red
""";
    Map<String, Integer> bag = Map.of("red", 12, "green", 13, "blue", 14);
    List<Integer> result = SnowIslandGame.findPossibleGames(input, bag);
    assertEquals(0, result.size());
  }

  @Test
  void findPossibleGames() {
    String input =
        """
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
""";
    Map<String, Integer> bag = Map.of("red", 12, "green", 13, "blue", 14);
    List<Integer> result = SnowIslandGame.findPossibleGames(input, bag);
    assertEquals(List.of(1, 2, 5), result);
  }

  @Test
  void canSumPossibleGames() {
    List<Integer> possibleGames = List.of(1, 2, 5);
    int result = SnowIslandGame.sumPossibleGames(possibleGames);
    assertEquals(8, result);
  }

  @Test
  void canSumFromInput() {
    String input =
        """
    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """;
    Map<String, Integer> bag = Map.of("red", 12, "green", 13, "blue", 14);
    int result = SnowIslandGame.calculateSumOfPossibleGames(input, bag);
    assertEquals(8, result);
  }

  @Test
  void smallestBagForRevealIsTheMaxOfEachColorWhenSingleColor() {
    List<Map<String, Integer>> reveals =
        List.of(Map.of("green", 2), Map.of("green", 1), Map.of("green", 3));
    Map<String, Integer> result = SnowIslandGame.smallesBagPossible(reveals);
    assertEquals(1, result.size());
    assertEquals(3, result.get("green"));
  }

  @Test
  void smallestBagForRevealIsTheMaxOfEachColor() {
    List<Map<String, Integer>> reveals =
        List.of(
            Map.of("green", 2, "blue", 4),
            Map.of("green", 1, "blue", 1),
            Map.of("green", 3, "blue", 2));
    Map<String, Integer> result = SnowIslandGame.smallesBagPossible(reveals);
    assertEquals(2, result.size());
    assertEquals(3, result.get("green"));
    assertEquals(4, result.get("blue"));
  }

  @Test
  void smallestBagForGameIsTheMaxOfEachColor() {
    String input = """
Game 1: 2 green, 4 blue; 1 green, 1 blue; 3 green, 2 blue
""";
    List<Map<String, Integer>> result = SnowIslandGame.smallesBagPossibleForGames(input);
    assertEquals(1, result.size());
    Map<String, Integer> gameResult = result.get(0);
    assertEquals(3, gameResult.get("green"));
    assertEquals(4, gameResult.get("blue"));
  }

  @Test
  void smallestBagForGameIsTheMaxOfEachColorWithExampleData() {
    String input =
        """
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
""";
    List<Map<String, Integer>> result = SnowIslandGame.smallesBagPossibleForGames(input);
    assertEquals(5, result.size());
    Map<String, Integer> secondGameResult = result.get(1);
    assertEquals(1, secondGameResult.get("red"));
    assertEquals(3, secondGameResult.get("green"));
    assertEquals(4, secondGameResult.get("blue"));
    Map<String, Integer> thirdGameResult = result.get(2);
    assertEquals(20, thirdGameResult.get("red"));
    assertEquals(13, thirdGameResult.get("green"));
    assertEquals(6, thirdGameResult.get("blue"));
  }

  @Test
  void canCalculateThePowerOfBag() {
    Map<String, Integer> bag = Map.of("red", 12, "green", 13, "blue", 14);
    int result = SnowIslandGame.calculatePowerOfBag(bag);
    assertEquals(12 * 13 * 14, result);
  }

  @Test
  void canCalculateThePowerOfEachGame() {
    String input =
            """
    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """;
    List<Integer> result = SnowIslandGame.powerOfGames(input);
    assertEquals(5, result.size());
    assertEquals(48, result.get(0));
    assertEquals(12, result.get(1));
    assertEquals(1560, result.get(2));
    assertEquals(630, result.get(3));
    assertEquals(36, result.get(4));
  }

  @Test
  void canCalculateTheSumOfPowers() {
    List<Integer> powers = List.of(48, 12, 1560, 630, 36);
    int result = SnowIslandGame.sumPossibleGames(powers);
    assertEquals(2286, result);
  }

  @Test
  void canCalculateTheSumOfPowerOfGames() {
    String input =
            """
    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """;
   int sum = SnowIslandGame.sumOfpowerOfGames(input);
   assertEquals(2286, sum);

  }

}