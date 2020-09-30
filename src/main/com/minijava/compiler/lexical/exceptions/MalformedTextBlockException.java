package com.minijava.compiler.lexical.exceptions;

public class MalformedTextBlockException extends LexicalException {
    private static final String ERROR_MESSAGE = "el bloque de texto %s no fue abierto correctamente.";

    public MalformedTextBlockException(int lineNumber, String lexeme, String line, int lexemePosition) {
        super(lineNumber, String.format(ERROR_MESSAGE, lexeme), lexeme, line, lexemePosition);
    }
}