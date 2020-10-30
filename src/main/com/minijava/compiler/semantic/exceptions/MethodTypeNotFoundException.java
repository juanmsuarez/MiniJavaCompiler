package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Method;
import com.minijava.compiler.semantic.entities.types.Type;

public class MethodTypeNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "el método %s retorna un tipo clase %s, que no fue declarado";

    public MethodTypeNotFoundException(Method method, Type type) {
        super(method.getLexeme(), buildErrorMessage(method.getName(), type.getName()));
    }

    private static String buildErrorMessage(String aClass, String type) {
        return String.format(ERROR_MESSAGE, aClass, type);
    }
}
