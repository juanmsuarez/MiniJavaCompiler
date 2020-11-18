package com.minijava.compiler.semantic.declarations.exceptions;

import com.minijava.compiler.semantic.declarations.entities.Attribute;

public class DuplicateAttributeException extends SemanticException {
    private static final String ERROR_MESSAGE = "atributo %s duplicado";

    public DuplicateAttributeException(Attribute attribute) {
        super(attribute.getLexeme(), buildErrorMessage(attribute.getName()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
