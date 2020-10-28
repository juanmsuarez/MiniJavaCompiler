package com.minijava.compiler.lexical.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class MalformedOperatorException extends LexicalException {
    private static final String ERROR_MESSAGE = "%s no es un operador válido.";

    public MalformedOperatorException(Lexeme lexeme) {
        super(lexeme, String.format(ERROR_MESSAGE, lexeme.getString()));
    }
}
