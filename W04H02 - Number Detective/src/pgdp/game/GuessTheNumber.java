package pgdp.game;

import pgdp.InputReader;
import pgdp.RandomNumberGenerator;

import java.util.Random;

import static pgdp.InputReader.readInt;

public class GuessTheNumber {

    public void guessTheNumber() {
        // TODO
        int points = 0;
        int lives = 3;
        int attempts = 1;
        int totalAttempts = 0;
        int difficulty = 0;
        boolean started = true;
        boolean helper = true;


        while (lives != 0) {
            if(!started){
                System.out.println("You have " + lives + " lives and " + points + " points.");
            }
            if (started){
                System.out.println("Hello, Number Detective!");
                System.out.println("You have " + lives + " lives and " + points + " points.");
                started = false;
            }
            printMenu();
            boolean i=true;
            int input=5;
            while (i) {
                int ask = readInt();
                if (ask <= 4 && ask >=1 ){
                    input = ask;
                    i = false;
                }
                else System.out.println("This was not a valid choice, please try again.");
            }
            if (input == 4) {
                System.out.println("Goodbye!");
                System.out.println("You are leaving with " + points + " points!");
                return;
            }
            else if (input == 1) {
                totalAttempts = 8;
                difficulty = 1;
                int solution = RandomNumberGenerator.getGenerator().generate(100);
                while (attempts <= totalAttempts) {
                    if ((attempts == totalAttempts) && points >=600) {
                        System.out.println("LAST ATTEMPT! Do you want to buy a hint for 600 points? (1) yes (2) no");
                        while (helper){
                            int intHint = readInt();
                            if (intHint > 2 || intHint < 1) {
                            System.out.println("This was not a valid choice, please try again.");
                            }
                            else helper = false;
                        if (intHint == 1) {
                                points -= 600;
                                if (solution % 2 == 0) {
                                    System.out.println("The number is even!");
                                } else {
                                    System.out.println("The number is odd!");
                                }
                            }
                        }
                    }
                    System.out.println("(" + attempts + "/" + totalAttempts + ") Enter your guess:");
                    attempts += 1;
                    int easyInput = readInt();
                    if (solution == easyInput) {
                        points += 200;
                        System.out.println("Congrats! You guessed the correct number.");
                        break;
                    }       else if ((easyInput > solution) && (attempts <= totalAttempts)) {
                        System.out.println("The number is lower.");}
                    else if ((easyInput < solution) && (attempts <= totalAttempts)) {System.out.println("The number is higher.");
                    }

                else if (attempts >= totalAttempts) {
                    lives -= 1;
                    System.out.println("Sorry, you've used all attempts. The correct number was " +solution+".");
                    if (lives == 0){
                        System.out.println("Game over! You are out of lives.");
                        break;}
                }}
            }else if (input == 2) {
                totalAttempts = 10;
                difficulty = 1;
                int solution = RandomNumberGenerator.getGenerator().generate(500);
                while (attempts <= totalAttempts) {
                    if ((attempts == totalAttempts) && points >=600) {
                        System.out.println("LAST ATTEMPT! Do you want to buy a hint for 600 points? (1) yes (2) no");
                        while (helper){
                            int intHint = readInt();
                            if (intHint > 2 || intHint < 1) {
                                System.out.println("This was not a valid choice, please try again.");
                            }
                            else helper = false;
                            if (intHint == 1) {
                                points -= 600;
                                if (solution % 2 == 0) {
                                    System.out.println("The number is even!");
                                } else {
                                    System.out.println("The number is odd!");
                                }
                            }
                        }
                    }
                    System.out.println("(" + attempts + "/" + totalAttempts + ") Enter your guess:");
                    attempts += 1;
                    int easyInput = readInt();
                    if (solution == easyInput) {
                        points += 200;
                        lives += 1;
                        System.out.println("Congrats! You guessed the correct number.");
                        break;
                    }
                    else if ((easyInput > solution) && (attempts <= totalAttempts)) {
                        System.out.println("The number is lower.");}
                    else if ((easyInput < solution) && (attempts <= totalAttempts)) {System.out.println("The number is higher.");
                    }

                else if (attempts >= totalAttempts) {
                    lives -= 1;
                    System.out.println("Sorry, you've used all attempts. The correct number was " +solution+".");
                    if (lives == 0){
                        System.out.println("Game over! You are out of lives.");
                        break;}}
                }
            }
            else if (input == 3) {
                totalAttempts = 10;
                difficulty = 1;
                int solution = RandomNumberGenerator.getGenerator().generate(1000);
                while (attempts <= totalAttempts) {
                    if ((attempts == totalAttempts) && points >=600) {
                        System.out.println("LAST ATTEMPT! Do you want to buy a hint for 600 points? (1) yes (2) no");
                        while (helper){
                            int intHint = readInt();
                            if (intHint > 2 || intHint < 1) {
                                System.out.println("This was not a valid choice, please try again.");
                            }
                            else helper = false;
                            if (intHint == 1) {
                                points -= 600;
                                if (solution % 2 == 0) {
                                    System.out.println("The number is even!");
                                } else {
                                    System.out.println("The number is odd!");
                                }
                            }
                        }
                    }
                    System.out.println("(" + attempts + "/" + totalAttempts + ") Enter your guess:");
                    attempts += 1;
                    int easyInput = readInt();
                    if (solution == easyInput) {
                        points += 500;
                        lives += 3;
                        System.out.println("Congrats! You guessed the correct number.");
                        break;
                    }       else if ((easyInput > solution) && (attempts <= totalAttempts)) {
                        System.out.println("The number is lower.");}
                    else if ((easyInput < solution) && (attempts <= totalAttempts)) {System.out.println("The number is higher.");
                    }

                else if (attempts >= totalAttempts) {
                    lives -= 1;
                    System.out.println("Sorry, you've used all attempts. The correct number was " +solution+".");
                    if (lives == 0){
                        System.out.println("Game over! You are out of lives.");
                        break;}}
                }
            }
            attempts = 1;
            helper = true;

        }
        System.out.println("You are leaving with " + points + " points!");
    }

    // <==================================== HELPER METHODS ====================================>

    private void printMenu() {
        System.out.println("Choose difficulty level to start a new game:\n" +
                "(1) Easy   [0;100)   8 Attempts, Reward: +200 Points\n" +
                "(2) Medium [0;500)  10 Attempts, Reward: +200 Points +1 Life\n" +
                "(3) Hard   [0;1000) 10 Attempts, Reward: +500 Points +3 Lives\n" +
                "(4) Exit");
    }

    public static void main(String[] args) {
        new GuessTheNumber().guessTheNumber();
    }

}
