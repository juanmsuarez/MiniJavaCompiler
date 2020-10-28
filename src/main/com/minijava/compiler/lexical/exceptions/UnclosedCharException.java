package com.minijava.compiler.lexical.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class UnclosedCharException extends LexicalException {
    private static final String ERROR_MESSAGE = "el literal de carácter %s no fue cerrado correctamente.";

    public UnclosedCharException(Lexeme lexeme) {
        super(lexeme, String.format(ERROR_MESSAGE, lexeme.getString()));
    }
}
