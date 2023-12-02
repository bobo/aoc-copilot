package org.volvocars;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class aoc2023_2 {

  public static void main(String[] args) throws IOException {
    String input = Files.readString(Path.of("src/main/resources/2023/input_2.txt"));
    Map<String, Integer> bag = Map.of("red", 12, "green", 13, "blue", 14);
    int result = SnowIslandGame.calculateSumOfPossibleGames(input, bag);
    System.out.println("sum of possiblegames:"+result);

    int sumOfPower = SnowIslandGame.sumOfpowerOfGames(input);
    System.out.println("sum of power of bags:"+sumOfPower);

  }
}
