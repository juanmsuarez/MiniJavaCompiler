package com.minijava.compiler.semantic.declarations.exceptions;

import com.minijava.compiler.semantic.declarations.entities.Constructor;

public class InvalidConstructorException extends SemanticException {
    private static final String ERROR_MESSAGE = "el constructor %s no coincide con el nombre de la clase";

    public InvalidConstructorException(Constructor constructor) {
        super(constructor.getLexeme(), buildErrorMessage(constructor.getName()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
