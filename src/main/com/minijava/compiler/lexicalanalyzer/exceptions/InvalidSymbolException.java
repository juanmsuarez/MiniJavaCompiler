package com.minijava.compiler.lexicalanalyzer.exceptions;

public class InvalidSymbolException extends LexicalException {
    private static final String ERROR_MESSAGE = "%s no es un símbolo válido.";

    public InvalidSymbolException(int lineNumber, String lexeme, String line, int lexemePosition) {
        super(lineNumber, String.format(ERROR_MESSAGE, lexeme), lexeme, line, lexemePosition);
    }
}
