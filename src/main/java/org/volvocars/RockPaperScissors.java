package org.volvocars;

public class RockPaperScissors {
    public String play(String player1, String player2) {
        if (player1.equals(player2)) {
            return "D";
        } else if ((player1.equals("A") && player2.equals("B")) ||
                   (player1.equals("B") && player2.equals("C")) ||
                   (player1.equals("C") && player2.equals("A"))) {
            return player1;
        } else {
            return player2;
        }
    }
}