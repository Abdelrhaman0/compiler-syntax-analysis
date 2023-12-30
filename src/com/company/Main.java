package com.company;

import java.util.Scanner;

class SimpleArithmeticParser {

    private String inputText;
    private int currentPosition;
    private char currentCharacter;

    public SimpleArithmeticParser(String text) {
        this.inputText = text;
        this.currentPosition = 0;
        this.currentCharacter = currentPosition < text.length() ? text.charAt(currentPosition) : '\0';
    }

    private void moveToNextChar() {
        currentPosition++;
        currentCharacter = currentPosition < inputText.length() ? inputText.charAt(currentPosition) : '\0';
    }

    private void skipWhitespace() {
        while (currentCharacter != '\0' && Character.isWhitespace(currentCharacter)) {
            moveToNextChar();
        }
    }

    private int parseInteger() {
        StringBuilder number = new StringBuilder();
        while (currentCharacter != '\0' && Character.isDigit(currentCharacter)) {
            number.append(currentCharacter);
            moveToNextChar();
        }
        return Integer.parseInt(number.toString());
    }

    private int parseFactor() {
        skipWhitespace();
        if (currentCharacter == '(') {
            moveToNextChar();
            int result = parseExpression();
            if (currentCharacter == ')') {
                moveToNextChar();
            }
            return result;
        } else if (Character.isDigit(currentCharacter)) {
            return parseInteger();
        }
        throw new RuntimeException("Invalid syntax");
    }

    private int parseTerm() {
        int result = parseFactor();
        skipWhitespace();
        while (currentCharacter == '*' || currentCharacter == '/') {
            if (currentCharacter == '*') {
                moveToNextChar();
                result *= parseFactor();
            } else if (currentCharacter == '/') {
                moveToNextChar();
                result /= parseFactor();
            }
            skipWhitespace();
        }
        return result;
    }

    public int parseExpression() {
        int result = parseTerm();
        skipWhitespace();
        while (currentCharacter == '+' || currentCharacter == '-') {
            if (currentCharacter == '+') {
                moveToNextChar();
                result += parseTerm();
            } else if (currentCharacter == '-') {
                moveToNextChar();
                result -= parseTerm();
            }
            skipWhitespace();
        }
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter any arithmetic operation: ");
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                continue;
            }

            SimpleArithmeticParser parser = new SimpleArithmeticParser(input);
            try {
                int result = parser.parseExpression();
                System.out.println("Result: " + result);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
