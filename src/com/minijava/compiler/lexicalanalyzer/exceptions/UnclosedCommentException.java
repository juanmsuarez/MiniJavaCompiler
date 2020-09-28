package com.minijava.compiler.lexicalanalyzer.exceptions;

public class UnclosedCommentException extends LexicalException {
    private static final String ERROR_MESSAGE = "comentario sin cerrar %s";

    public UnclosedCommentException(int lineNumber, String lexeme, String line, int lexemePosition) {
        super(lineNumber, String.format(ERROR_MESSAGE, lexeme), lexeme, line, lexemePosition);

    }
}
