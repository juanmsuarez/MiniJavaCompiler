package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Unit;

public class DuplicateUnitException extends SemanticException {
    private static final String ERROR_MESSAGE = "ya existe una clase o interfaz con el nombre %s";

    public DuplicateUnitException(Unit unit) {
        super(unit.getLexeme(), buildErrorMessage(unit.getName()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
