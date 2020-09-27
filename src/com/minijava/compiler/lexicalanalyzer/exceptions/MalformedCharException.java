package com.minijava.compiler.lexicalanalyzer.exceptions;

public class MalformedCharException extends LexicalException {
    public MalformedCharException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }

    @Override
    public String toString() {
        return String.format("Error léxico en línea %d: %s no sé encontró un carácter válido luego de la apertura del " +
                "literal.\n", lineNumber, lexeme) + super.toString();
    }
}
