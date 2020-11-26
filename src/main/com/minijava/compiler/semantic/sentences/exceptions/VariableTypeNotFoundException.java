package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.semantic.declarations.entities.types.ReferenceType;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class VariableTypeNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "no se pueden declarar variables de tipo %s (no fue declarado)";

    public VariableTypeNotFoundException(ReferenceType type) {
        super(type.getLexeme(), buildErrorMessage(type));
    }

    private static String buildErrorMessage(ReferenceType type) {
        return String.format(ERROR_MESSAGE, type.getName());
    }
}
