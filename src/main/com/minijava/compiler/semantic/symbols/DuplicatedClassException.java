package com.minijava.compiler.semantic.symbols;

import com.minijava.compiler.semantic.exceptions.SemanticException;

public class DuplicatedClassException extends SemanticException {
    private static final String ERROR_MESSAGE = "clase %s duplicada";

    public DuplicatedClassException(Class duplicatedClass) {
        super(duplicatedClass.getLexeme(), buildErrorMessage(duplicatedClass.getName()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
