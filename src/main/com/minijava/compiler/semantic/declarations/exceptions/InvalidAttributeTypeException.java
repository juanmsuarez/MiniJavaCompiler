package com.minijava.compiler.semantic.declarations.exceptions;

import com.minijava.compiler.semantic.declarations.entities.Attribute;
import com.minijava.compiler.semantic.declarations.entities.types.ReferenceType;

public class InvalidAttributeTypeException extends SemanticException {
    private static final String ERROR_MESSAGE = "el atributo %s es de tipo inválido %s (no fue declarado o no cumple reglas de genericidad)";
    private static final String GENERIC_ERROR_MESSAGE = "el atributo %s es de tipo inválido %s<%s> (no fue declarado o no cumple reglas de genericidad)";

    public InvalidAttributeTypeException(Attribute attribute, ReferenceType type) {
        super(attribute.getLexeme(), buildErrorMessage(attribute.getName(), type));
    }

    private static String buildErrorMessage(String attribute, ReferenceType type) {
        if (type.getGenericType() == null) {
            return String.format(ERROR_MESSAGE, attribute, type.getName());
        } else {
            return String.format(GENERIC_ERROR_MESSAGE, attribute, type.getName(), type.getGenericType());
        }
    }
}
