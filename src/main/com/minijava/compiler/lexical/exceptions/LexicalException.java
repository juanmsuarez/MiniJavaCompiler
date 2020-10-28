package com.minijava.compiler.lexical.exceptions;

import com.minijava.compiler.CompilerException;
import com.minijava.compiler.lexical.analyzer.Lexeme;

public abstract class LexicalException extends CompilerException {
    private static final String ERROR_TYPE = "léxico";

    public LexicalException(Lexeme lexeme, String errorMessage) {
        super(ERROR_TYPE, lexeme, errorMessage);
    }

    protected static String shortenString(String string) {
        return string.substring(0, string.indexOf('\n'));
    }
}
