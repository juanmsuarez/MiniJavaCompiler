package com.minijava.compiler.lexicalanalyzer;

public class Token {
    private String name;
    private String lexeme;
    private int lineNumber;

    public Token(String name, String lexeme, int lineNumber) {
        this.name = name;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%d)", name, lexeme, lineNumber);
    }
}
