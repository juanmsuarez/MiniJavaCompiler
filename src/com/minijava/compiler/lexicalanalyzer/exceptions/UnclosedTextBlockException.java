package com.minijava.compiler.lexicalanalyzer.exceptions;

public class UnclosedTextBlockException extends LexicalException {
    private static final String ERROR_MESSAGE = "el bloque de texto %s no fue cerrado correctamente.";

    public UnclosedTextBlockException(int lineNumber, String lexeme, String line, int lexemePosition) {
        super(lineNumber, String.format(ERROR_MESSAGE, lexeme), lexeme, line, lexemePosition);
    }
}