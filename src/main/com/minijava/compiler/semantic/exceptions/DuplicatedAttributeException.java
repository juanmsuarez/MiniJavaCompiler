package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Attribute;

public class DuplicatedAttributeException extends SemanticException {
    private static final String ERROR_MESSAGE = "atributo %s duplicado";

    public DuplicatedAttributeException(Attribute attribute) {
        super(attribute.getLexeme(), buildErrorMessage(attribute.getName()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
