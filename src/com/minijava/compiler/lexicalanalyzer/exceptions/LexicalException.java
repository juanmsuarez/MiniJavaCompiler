package com.minijava.compiler.lexicalanalyzer.exceptions;

public abstract  class LexicalException extends Exception {
    protected String lexeme;
    protected int lineNumber;

    public LexicalException(String lexeme, int lineNumber) {
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return String.format("[Error:%s|%d]", lexeme, lineNumber);
    }
}
