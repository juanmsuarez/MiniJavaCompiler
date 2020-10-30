package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Constructor;

public class DuplicatedConstructorException extends SemanticException {
    private static final String ERROR_MESSAGE = "constructor %s duplicado";

    public DuplicatedConstructorException(Constructor constructor) {
        super(constructor.getLexeme(), buildErrorMessage(constructor.getName()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
