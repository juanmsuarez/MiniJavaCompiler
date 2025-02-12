package com.minijava.compiler.semantic.declarations.exceptions;

import com.minijava.compiler.semantic.declarations.entities.Method;

public class DuplicateMethodException extends SemanticException {
    private static final String ERROR_MESSAGE = "método %s duplicado";

    public DuplicateMethodException(Method method) {
        super(method.getLexeme(), buildErrorMessage(method.getName()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
