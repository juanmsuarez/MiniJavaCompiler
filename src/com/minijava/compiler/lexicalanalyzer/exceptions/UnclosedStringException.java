package com.minijava.compiler.lexicalanalyzer.exceptions;

public class UnclosedStringException extends LexicalException {
    public UnclosedStringException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }

    @Override
    public String toString() {
        return String.format("Error léxico en línea %d: el literal de String %s no fue cerrado correctamente.\n", lineNumber, lexeme)
                + super.toString();
    }
}