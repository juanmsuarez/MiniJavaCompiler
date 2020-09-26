package com.minijava.compiler.lexicalanalyzer.exceptions;

public class InvalidSymbolException extends LexicalException {

    public InvalidSymbolException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }

    @Override
    public String toString() {
        return String.format("Error léxico en línea %d: %s no es un símbolo válido.\n", lineNumber, lexeme)
                + super.toString();
    }
}
