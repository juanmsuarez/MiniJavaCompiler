package com.minijava.compiler.lexical.analyzer;

public class Lexeme {
    private String string;
    private int lineNumber;
    private String line;
    private int lexemePosition;

    public Lexeme(String name, int lineNumber, String line, int lexemePosition) {
        this.string = name;
        this.lineNumber = lineNumber;
        this.line = line;
        this.lexemePosition = lexemePosition;
    }

    public String getString() {
        return string;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getLine() {
        return line;
    }

    public int getLexemePosition() {
        return lexemePosition;
    }

    @Override
    public String toString() {
        return getString();
    }
}
