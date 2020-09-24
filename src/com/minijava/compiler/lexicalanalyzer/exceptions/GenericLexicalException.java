package com.minijava.compiler.lexicalanalyzer.exceptions;

public class GenericLexicalException extends LexicalException {

    public GenericLexicalException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }

    @Override
    public String toString() {
        return String.format("Error léxico en línea %d: %s no es un lexema válido.\n", lineNumber, lexeme)
                + super.toString();
    }
}
