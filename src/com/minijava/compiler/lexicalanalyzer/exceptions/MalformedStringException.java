package com.minijava.compiler.lexicalanalyzer.exceptions;

public class MalformedStringException extends LexicalException {
    public MalformedStringException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }

    @Override
    public String toString() {
        return String.format("Error léxico en línea %d: %s no es un literal de String válido.\n", lineNumber, lexeme)
                + super.toString();
    }
}