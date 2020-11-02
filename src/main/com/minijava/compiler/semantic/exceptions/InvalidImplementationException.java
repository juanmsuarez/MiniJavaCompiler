package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Class;
import com.minijava.compiler.semantic.entities.Method;

public class InvalidImplementationException extends SemanticException {
    private static final String ERROR_MESSAGE = "la clase %s no implementa correctamente el método %s definido en una de sus interfaces";

    public InvalidImplementationException(Class aClass, Method method) {
        super(aClass.getLexeme(), buildErrorMessage(aClass.getName(), method.getName()));
    }

    private static String buildErrorMessage(String className, String methodName) {
        return String.format(ERROR_MESSAGE, className, methodName);
    }
}
