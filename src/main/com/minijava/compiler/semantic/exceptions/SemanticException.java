package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.CompilerException;
import com.minijava.compiler.lexical.analyzer.Lexeme;

public class SemanticException extends CompilerException {
    private static final String ERROR_TYPE = "semántico";

    public SemanticException(Lexeme lexeme, String errorMessage) {
        super(ERROR_TYPE, lexeme, errorMessage);
    }
}