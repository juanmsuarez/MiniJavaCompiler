package com.minijava.compiler.semantic.declarations.exceptions;

import com.minijava.compiler.semantic.declarations.entities.Method;

public class InvalidRedefinitionException extends SemanticException {
    private static final String ERROR_MESSAGE = "el método %s tiene el mismo nombre que el método de un ancestro pero difiere en su signatura";

    public InvalidRedefinitionException(Method method) {
        super(method.getLexeme(), buildErrorMessage(method.getName()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
