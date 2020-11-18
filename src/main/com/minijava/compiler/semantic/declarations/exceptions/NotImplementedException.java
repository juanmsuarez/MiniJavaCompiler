package com.minijava.compiler.semantic.declarations.exceptions;

import com.minijava.compiler.semantic.declarations.entities.Class;
import com.minijava.compiler.semantic.declarations.entities.Method;

public class NotImplementedException extends SemanticException {
    private static final String ERROR_MESSAGE = "la clase %s no implementa el método %s definido en una de sus interfaces";

    public NotImplementedException(Class aClass, Method method) {
        super(aClass.getLexeme(), buildErrorMessage(aClass.getName(), method.getName()));
    }

    private static String buildErrorMessage(String className, String methodName) {
        return String.format(ERROR_MESSAGE, className, methodName);
    }
}
