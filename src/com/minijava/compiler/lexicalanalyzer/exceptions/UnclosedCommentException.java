package com.minijava.compiler.lexicalanalyzer.exceptions;

public class UnclosedCommentException extends LexicalException {
    private static final String ERROR_MESSAGE = "comentario sin cerrar, comenzando con %s";

    public UnclosedCommentException(int lineNumber, String lexeme, String line, int lexemePosition) {
        super(lineNumber, String.format(ERROR_MESSAGE, lexeme.substring(0, lexeme.indexOf('\n'))), lexeme, line, lexemePosition);
    }
}
