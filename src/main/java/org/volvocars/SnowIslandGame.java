package org.volvocars;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SnowIslandGame {
  private static final Pattern GAME_NUMBER_PATTERN = Pattern.compile("Game (\\d+):");

  public static int parseGameNumber(String input) {
    Matcher matcher = GAME_NUMBER_PATTERN.matcher(input);
    if (matcher.find()) {
      return Integer.parseInt(matcher.group(1));
    } else {
      throw new IllegalArgumentException("Input does not contain a valid game number");
    }
  }

  private static final Pattern GAME_DETAILS_PATTERN = Pattern.compile("Game \\d+: (.*)");

  public static String parseGame(String input) {
    Matcher matcher = GAME_DETAILS_PATTERN.matcher(input);
    if (matcher.find()) {
      return matcher.group(1);
    } else {
      throw new IllegalArgumentException("Input does not contain valid game details");
    }
  }

  public static List<String> parseReveals(String input) {
    return Arrays.asList(input.split("; "));
  }

  public static List<String> parseReveal(String input) {
    return Arrays.asList(input.split(", "));
  }

  public static Map<String, Integer> convertRevealToMap(List<String> input) {
    Map<String, Integer> result = new HashMap<>();
    for (String reveal : input) {
      String[] parts = reveal.split(" ");
      int number = Integer.parseInt(parts[0]);
      String color = parts[1];
      result.put(color, number);
    }
    return result;
  }

  public static boolean isRevealPossible(Map<String, Integer> game, Map<String, Integer> bag) {
    for (Map.Entry<String, Integer> entry : game.entrySet()) {
      String color = entry.getKey();
      int count = entry.getValue();
      if (bag.getOrDefault(color, 0) < count) {
        return false;
      }
    }
    return true;
  }
  public static boolean isGamePossible(List<Map<String, Integer>> game, Map<String, Integer> bag) {
    for (Map<String, Integer> reveal : game) {
      if (!isRevealPossible(reveal, bag)) {
        return false;
      }
    }
    return true;
  }

  public static List<Integer> findPossibleGames(String input, Map<String, Integer> bag) {
    String[] games = input.split("\n");
    List<Integer> result = new ArrayList<>();
    for (String game : games) {
      int gameNumber = parseGameNumber(game);
      List<Map<String, Integer>> reveals = convertGameToReveals(game);
      if (isGamePossible(reveals, bag)) {
        result.add(gameNumber);
      }
    }
    return result;
  }
  public static int sumPossibleGames(List<Integer> games) {
    return games.stream().reduce(Integer::sum).orElse(0);
  }

  public static List<Map<String, Integer>> convertGameToReveals(String input) {
    String gameDetails = parseGame(input);
    List<String> reveals = parseReveals(gameDetails);
    List<Map<String, Integer>> result = new ArrayList<>();
    for (String reveal : reveals) {
      result.add(convertRevealToMap(parseReveal(reveal)));
    }
    return result;
  }
  public static int calculateSumOfPossibleGames(String input, Map<String, Integer> bag) {
    List<Integer> possibleGames = findPossibleGames(input, bag);
    return sumPossibleGames(possibleGames);
  }

  public static Map<String, Integer> smallesBagPossible(List<Map<String, Integer>> reveals) {
    Map<String, Integer> result = new HashMap<>();
    for (Map<String, Integer> reveal : reveals) {
      for (Map.Entry<String, Integer> entry : reveal.entrySet()) {
        String color = entry.getKey();
        int count = entry.getValue();
        result.put(color, Math.max(result.getOrDefault(color, 0), count));
      }
    }
    return result;
  }
  public static List<Map<String, Integer>> smallesBagPossibleForGames(String input) {
    String[] games = input.split("\n");
    List<Map<String, Integer>> result = new ArrayList<>();
    for (String game : games) {
      List<Map<String, Integer>> reveals = convertGameToReveals(game);
      result.add(smallesBagPossible(reveals));
    }
    return result;
  }
  public static int calculatePowerOfBag(Map<String, Integer> bag) {
    return bag.values().stream().reduce(1, (a, b) -> a * b);
  }
  public static List<Integer> powerOfGames(String input) {
    String[] games = input.split("\n");
    List<Integer> result = new ArrayList<>();
    for (String game : games) {
      List<Map<String, Integer>> reveals = convertGameToReveals(game);
      Map<String, Integer> smallestBag = smallesBagPossible(reveals);
      result.add(calculatePowerOfBag(smallestBag));
    }
    return result;
  }

  public static int sumOfpowerOfGames(String input) {
    String[] games = input.split("\n");
    List<Integer> powers = new ArrayList<>();
    for (String game : games) {
      List<Map<String, Integer>> reveals = convertGameToReveals(game);
      Map<String, Integer> smallestBag = smallesBagPossible(reveals);
      powers.add(calculatePowerOfBag(smallestBag));
    }
    return sumPossibleGames(powers);
  }
}
