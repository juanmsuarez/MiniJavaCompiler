package com.minijava.compiler.lexicalanalyzer.exceptions;

public class UnclosedCharException extends LexicalException {
    public UnclosedCharException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }

    @Override
    public String toString() {
        return String.format("Error léxico en línea %d: el literal de carácter %s no fue cerrado correctamente.\n", lineNumber, lexeme)
                + super.toString();
    }
}
