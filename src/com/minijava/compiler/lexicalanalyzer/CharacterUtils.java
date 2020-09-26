package com.minijava.compiler.lexicalanalyzer;

class CharacterUtils {
    public static boolean isEOF(Character character) {
        return character == null;
    }

    public static boolean isEOL(Character character) {
        return character != null && character == '\n';
    }

    public static boolean isWhiteSpace(Character character) {
        return character != null && (character == ' ' || character == '\t' || character == '\n');
    }

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

    public static boolean isSingleQuote(Character character) {
        return character != null && character == '\'';
    }

    public static boolean isDoubleQuote(Character character) {
        return character != null && character == '"';
    }

    public static boolean isBackslash(Character character) {
        return character != null && character == '\\';
    }

    public static boolean isPunctuation(Character character) {
        return character != null && "(){};,.".contains(character.toString());
    }
}
