package com.hangman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArrayList<String> wordBuilder = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader("E:\\Users\\strad\\IdeaProjects\\Hangman\\src\\com\\hangman\\Hangman.txt"))) {
            String line;
            while((line = br.readLine()) != null) {
                wordBuilder.add(line.trim().toUpperCase());
            }
        }

        String[] wordList = wordBuilder.toArray(new String[wordBuilder.size()]);
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        String chosenWord;
        StringBuilder userWord;
        String rawInput;
        int tries;
        boolean won;
        char guessChar;

        while(true) {
            tries = 5;
            won = false;
            chosenWord = chooseWord(wordList, rand);
            userWord = initUserWord(chosenWord);
            System.out.println("Welcome to hangman");
            while(tries > 0) {
                printStatus(userWord, tries);
                rawInput = scanner.nextLine();
                if(rawInput.length() > 0) {
                    guessChar = Character.toUpperCase(rawInput.charAt(0));
                } else {
                    continue;
                }
                if(evalUserInput(guessChar, chosenWord, userWord)) {
                    System.out.println("You go the right letter");
                } else {
                    System.out.println("Try again");
                    tries--;
                }
                if(userHasWon(userWord)) {
                    won = true;
                    break;
                }
            }
            if(won) {
                System.out.println("You have won");
            } else {
                System.out.println("You have lost");
            }
            System.out.println("The word was " + chosenWord);
            rawInput = scanner.nextLine();
            if(rawInput.length() > 0) {
                guessChar = Character.toUpperCase(rawInput.charAt(0));
            } else {
                guessChar = 'N';
            }
            if(guessChar == 'N') {
                break;
            }
        }
        scanner.close();
    }
    private static boolean userHasWon(StringBuilder userWord) {
        for(int i = 0; i < userWord.length(); i++) {
            if(userWord.charAt(i) == '_') {
                return false;
            }
        }
        return true;
    }

    public static boolean evalUserInput(char guessChar, String chosenWord, StringBuilder userWord) {
        boolean found = false;
        for(int i = 0; i < chosenWord.length(); i++) {
            if(guessChar == chosenWord.charAt(i)) {
                found = true;
                userWord.setCharAt(i, guessChar);
            }
        }
        return found;
    }

    private static void printStatus(StringBuilder userWord, int tries) {
        System.out.println("\n\n");
        System.out.println(userWord);
        System.out.println("Tries: " + tries);
        System.out.println("Enter a character: ");
    }

    public static StringBuilder initUserWord(String chosenWord) {
        StringBuilder result = new StringBuilder(chosenWord);
        for(int i = 0; i < result.length(); i++) {
            result.setCharAt(i, '_');
        }
        return result;
    }

    private static String chooseWord(String[] wordList, Random rand) {
        return wordList[rand.nextInt(wordList.length)];
    }
}

