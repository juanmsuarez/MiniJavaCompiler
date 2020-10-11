package com.minijava.compiler.lexical.exceptions;

import com.minijava.compiler.CompilerException;

public abstract class LexicalException extends CompilerException {
    private static final String ERROR_TYPE = "léxico";

    public LexicalException(int lineNumber, String errorMessage, String lexeme, String line, int lexemePosition) {
        super(ERROR_TYPE, lineNumber, errorMessage, lexeme, line, lexemePosition);
    }
}
