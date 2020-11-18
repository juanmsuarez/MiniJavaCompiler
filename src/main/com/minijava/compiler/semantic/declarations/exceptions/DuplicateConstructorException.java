package com.minijava.compiler.semantic.declarations.exceptions;

import com.minijava.compiler.semantic.declarations.entities.Constructor;

public class DuplicateConstructorException extends SemanticException {
    private static final String ERROR_MESSAGE = "constructor %s duplicado";

    public DuplicateConstructorException(Constructor constructor) {
        super(constructor.getLexeme(), buildErrorMessage(constructor.getName()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
