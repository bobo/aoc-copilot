package org.volvocars;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FoodCalculator {

  public int calculateElfFood(List<Integer> foods) {
    return foods.stream().mapToInt(Integer::intValue).sum();
  }

  public List<List<Integer>> splitElfFood(String elfs) {
    return Arrays.stream(elfs.split("\n\n"))
            .map(s -> Arrays.stream(s.split("\n"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()))
            .collect(Collectors.toList());
  }

  public  List<Integer> sumLists(List<List<Integer>> lists) {
    return lists.stream()
            .map(list -> list.stream().mapToInt(Integer::intValue).sum())
            .collect(Collectors.toList());
  }

  public int findBiggest(String elfs) {
    List<List<Integer>> elfLists = splitElfFood(elfs);
    List<Integer> sums = sumLists(elfLists);
    return Collections.max(sums);
  }
  public List<Integer> findTop3(String elfs) {
    List<List<Integer>> elfLists = splitElfFood(elfs);
    List<Integer> sums = sumLists(elfLists);
    return sums.stream()
            .sorted(Collections.reverseOrder())
            .limit(3)
            .collect(Collectors.toList());
  }

  public int findTop3Sum(String elfs) {
    List<Integer> top3 = findTop3(elfs);
    return top3.stream().mapToInt(Integer::intValue).sum();
  }
}