package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Method;
import com.minijava.compiler.semantic.entities.types.ReferenceType;

public class InvalidMethodTypeException extends SemanticException {
    private static final String ERROR_MESSAGE = "el método %s retorna un tipo inválido %s (no fue declarado o no cumple reglas de genericidad)";
    private static final String GENERIC_ERROR_MESSAGE = "el método %s retorna un tipo inválido %s<%s> (no fue declarado o no cumple reglas de genericidad)";

    public InvalidMethodTypeException(Method method, ReferenceType type) {
        super(method.getLexeme(), buildErrorMessage(method.getName(), type));
    }

    private static String buildErrorMessage(String attribute, ReferenceType type) {
        if (type.getGenericType() == null) {
            return String.format(ERROR_MESSAGE, attribute, type.getName());
        } else {
            return String.format(GENERIC_ERROR_MESSAGE, attribute, type.getName(), type.getGenericType());
        }
    }
}
