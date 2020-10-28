package com.minijava.compiler.lexical.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class UnclosedStringException extends LexicalException {
    private static final String ERROR_MESSAGE = "el literal de String %s no fue cerrado correctamente.";

    public UnclosedStringException(Lexeme lexeme) {
        super(lexeme, String.format(ERROR_MESSAGE, lexeme.getString()));
    }
}