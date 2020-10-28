package com.minijava.compiler.lexical.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class MalformedCharException extends LexicalException {
    private static final String ERROR_MESSAGE = "%s no se encontró un carácter válido luego de la apertura del literal.";

    public MalformedCharException(Lexeme lexeme) {
        super(lexeme, String.format(ERROR_MESSAGE, lexeme.getString()));
    }
}
