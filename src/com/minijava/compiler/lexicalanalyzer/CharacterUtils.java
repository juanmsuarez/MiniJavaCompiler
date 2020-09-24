package com.minijava.compiler.lexicalanalyzer;

public class CharacterUtils {
    public static boolean isUnderscore(Character character) {
        return character != null && character == '_';
    }

    public static boolean isDigit(Character character) {
        return character != null && character >= '0' && character <= '9';
    }

    public static boolean isLetter(Character character) {
        return isUpperCase(character) || isLowerCase(character);
    }

    public static boolean isUpperCase(Character character) {
        return character != null && character >= 'A' && character <= 'Z';
    }

    public static boolean isLowerCase(Character character) {
        return character != null && character >= 'a' && character <= 'z';
    }
}
