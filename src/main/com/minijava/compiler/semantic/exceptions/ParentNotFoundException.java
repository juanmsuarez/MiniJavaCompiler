package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Unit;

public class ParentNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "la clase o interfaz %s hereda de %s, que no fue declarada";

    public ParentNotFoundException(Unit unit, String parent) {
        super(unit.getLexeme(), buildErrorMessage(unit.getName(), parent));
    }

    private static String buildErrorMessage(String unitName, String parent) {
        return String.format(ERROR_MESSAGE, unitName, parent);
    }
}
