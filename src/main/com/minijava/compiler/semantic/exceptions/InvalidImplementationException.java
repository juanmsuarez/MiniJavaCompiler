package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Method;

public class InvalidImplementationException extends SemanticException {
    private static final String ERROR_MESSAGE = "el método %s tiene el mismo nombre que el método de una de sus interfaces pero difiere en su signatura";

    public InvalidImplementationException(Method method) {
        super(method.getLexeme(), buildErrorMessage(method.getName()));
    }

    private static String buildErrorMessage(String methodName) {
        return String.format(ERROR_MESSAGE, methodName);
    }
}
