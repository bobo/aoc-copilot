
Solving Advent of code using TDD with github copilot writing the code, and i write the test.

# Rules
* No code written by me, only tests
* Try not to look at the written code (i have failed this already, but do my best)


# Learnings
* Copilot goes nuts if the test tests to much, have to break things apart way more then usual.
* Copilot gets stuck in thought loops and to much context sometimes, restarting intellij seems to help.
* It is very hard to write tests for code you don't know, and to get the code to do what you want.
* Sometimes copilot makes the intention of the test pass, even though the input is wrong. example: i had forgotten the semicolon in "Game 3: 8 green, 6 blue, 20 red; 1 green, 1 blue" when i made a test to split the game into reveals, the code copilot made failed at first, until i added my semicolon.