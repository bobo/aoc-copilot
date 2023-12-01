package org.volvocars;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodCalculatorTest {
private final FoodCalculator foodCalculator = new FoodCalculator();
  @Test
  void canCalculateFoodForElf() {
    List foods = List.of(1000, 2000, 3000);
    int result = foodCalculator.calculateElfFood(foods);
    assertEquals(6000, result);
  }

  @Test
  void convertListToArrays() {
    String elfs = """
1000

2000

3000
""".trim();
    List<List<Integer>> elfLists = foodCalculator.splitElfFood(elfs);
    assertEquals(3, elfLists.size());
    assertEquals(1000, elfLists.get(0).get(0));
  }

  @Test
  void convertElfListToSums() {
    List<List<Integer>> elfLists = List.of(List.of(1000, 1000), List.of(2000, 3000));
    List<Integer> sums = foodCalculator.sumLists(elfLists);
    assertEquals(2, sums.size());
    assertEquals(2000, sums.get(0));
    assertEquals(5000, sums.get(1));
  }

  @Test
  void findBiggest() {
    String elfs = """
1000

2000

3000
""".trim();

    int biggest = foodCalculator.findBiggest(elfs);
    assertEquals(3000, biggest);
  }

  @Test
  void findBiggest2() {
    String elfs = """
1000
1000
1000
1000

2000

3000
""".trim();

    int biggest = foodCalculator.findBiggest(elfs);
    assertEquals(4000, biggest);
  }

  @Test
  void findBiggestTop3() {
    String elfs = """
1000
1000
1000
1000

2000

3000
""".trim();

    List<Integer> biggest = foodCalculator.findTop3(elfs);
    assertEquals(4000, biggest.get(0));
    assertEquals(3000, biggest.get(1));
    assertEquals(2000, biggest.get(2));
  }


  @Test
  void findTop3Sum() {
    String elfs = """
1000
1000
1000
1000

2000

3000
""".trim();

    int top3Sum = foodCalculator.findTop3Sum(elfs);
    assertEquals(9000, top3Sum);
  }
}
