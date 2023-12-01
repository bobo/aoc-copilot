package org.volvocars;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class aoc2022_1 {
    public static void main(String[] args) throws IOException {
        String elfs = Files.readString(Path.of("src/main/resources/inputs/2022/input.txt"));
        int result = new FoodCalculator().findBiggest(elfs);
        System.out.println(result);
    }

}
