package com.minijava.compiler.lexicalanalyzer.exceptions;

public class UnclosedStringException extends LexicalException {
    private static final String ERROR_MESSAGE = "el literal de String %s no fue cerrado correctamente.";

    public UnclosedStringException(int lineNumber, String lexeme, String line, int lexemePosition) {
        super(lineNumber, String.format(ERROR_MESSAGE, lexeme), lexeme, line, lexemePosition);
    }
}