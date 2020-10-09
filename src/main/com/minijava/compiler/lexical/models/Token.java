package com.minijava.compiler.lexical.models;

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

    public String getLexeme() {
        return lexeme;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%d)", name, lexeme, lineNumber + 1);
    }
}
