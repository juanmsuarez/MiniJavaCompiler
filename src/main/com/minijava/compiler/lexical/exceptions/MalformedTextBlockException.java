package com.minijava.compiler.lexical.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class MalformedTextBlockException extends LexicalException {
    private static final String ERROR_MESSAGE = "el bloque de texto %s no fue abierto correctamente.";

    public MalformedTextBlockException(Lexeme lexeme) {
        super(lexeme, String.format(ERROR_MESSAGE, lexeme.getString()));
    }
}