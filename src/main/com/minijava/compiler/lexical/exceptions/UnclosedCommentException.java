package com.minijava.compiler.lexical.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class UnclosedCommentException extends LexicalException {
    private static final String ERROR_MESSAGE = "comentario sin cerrar, comenzando con %s";

    public UnclosedCommentException(Lexeme lexeme) {
        super(lexeme, String.format(ERROR_MESSAGE, shortenString(lexeme.getString())));
    }
}
