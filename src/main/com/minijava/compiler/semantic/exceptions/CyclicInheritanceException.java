package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Unit;

public class CyclicInheritanceException extends SemanticException {
    private static final String ERROR_MESSAGE = "herencia cíclica involucrando a los ancestros de la clase o interfaz %s";

    public CyclicInheritanceException(Unit unit) {
        super(unit.getLexeme(), buildErrorMessage(unit.getName()));
    }

    private static String buildErrorMessage(String unitName) {
        return String.format(ERROR_MESSAGE, unitName);
    }
}