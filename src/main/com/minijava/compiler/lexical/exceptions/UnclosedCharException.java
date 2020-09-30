package com.minijava.compiler.lexical.exceptions;

public class UnclosedCharException extends LexicalException {
    private static final String ERROR_MESSAGE = "el literal de carácter %s no fue cerrado correctamente.";

    public UnclosedCharException(int lineNumber, String lexeme, String line, int lexemePosition) {
        super(lineNumber, String.format(ERROR_MESSAGE, lexeme), lexeme, line, lexemePosition);
    }
}
