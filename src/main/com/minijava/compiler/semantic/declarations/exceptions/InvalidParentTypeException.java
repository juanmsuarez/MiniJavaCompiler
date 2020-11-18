package com.minijava.compiler.semantic.declarations.exceptions;

import com.minijava.compiler.semantic.declarations.entities.Unit;
import com.minijava.compiler.semantic.declarations.entities.types.ReferenceType;

public class InvalidParentTypeException extends SemanticException {
    private static final String ERROR_MESSAGE = "la clase o interfaz %s hereda de un tipo inválido %s (no fue declarado o no cumple reglas de genericidad)";
    private static final String GENERIC_ERROR_MESSAGE = "la clase o interfaz %s hereda de un tipo inválido %s<%s> (no fue declarado o no cumple reglas de genericidad)";

    public InvalidParentTypeException(Unit unit, ReferenceType parentType) {
        super(unit.getLexeme(), buildErrorMessage(unit.getName(), parentType));
    }

    private static String buildErrorMessage(String attribute, ReferenceType type) {
        if (type.getGenericType() == null) {
            return String.format(ERROR_MESSAGE, attribute, type.getName());
        } else {
            return String.format(GENERIC_ERROR_MESSAGE, attribute, type.getName(), type.getGenericType());
        }
    }
}
