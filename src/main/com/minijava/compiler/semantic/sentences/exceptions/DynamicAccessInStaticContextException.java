package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class DynamicAccessInStaticContextException extends SemanticException {
    private static final String ERROR_MESSAGE = "no es posible acceder a %s desde un contexto estático";

    public DynamicAccessInStaticContextException(Lexeme access) {
        super(access, buildErrorMessage(access.getString()));
    }

    private static String buildErrorMessage(String access) {
        return String.format(ERROR_MESSAGE, access);
    }
}
