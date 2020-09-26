package com.minijava.compiler.lexicalanalyzer.exceptions;

public class MalformedCharException extends LexicalException {
    public MalformedCharException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }

    @Override
    public String toString() {
        return String.format("Error léxico en línea %d: %s no es un literal de carácter válido.\n", lineNumber, lexeme)
                + super.toString();
    }
}
