package com.minijava.compiler.lexicalanalyzer.exceptions;

public class MalformedOperatorException extends LexicalException {
    public MalformedOperatorException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }

    @Override
    public String toString() {
        return String.format("Error léxico en línea %d: %s no es un operador válido.\n", lineNumber, lexeme)
                + super.toString();
    }
}
