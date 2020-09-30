package com.minijava.compiler.lexical.exceptions;

public class UnclosedTextBlockException extends LexicalException {
    private static final String ERROR_MESSAGE = "el bloque de texto comenzado por %s no fue cerrado correctamente.";

    public UnclosedTextBlockException(int lineNumber, String lexeme, String line, int lexemePosition) {
        super(lineNumber, String.format(ERROR_MESSAGE, lexeme.substring(0, lexeme.indexOf('\n'))), lexeme, line, lexemePosition);
    }
}