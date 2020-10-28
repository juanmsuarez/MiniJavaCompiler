package com.minijava.compiler.lexical.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class UnclosedTextBlockException extends LexicalException {
    private static final String ERROR_MESSAGE = "el bloque de texto comenzado por %s no fue cerrado correctamente.";

    public UnclosedTextBlockException(Lexeme lexeme) {
        super(lexeme, String.format(ERROR_MESSAGE, shortenString(lexeme.getString())));
    }
}