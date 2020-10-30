package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Attribute;
import com.minijava.compiler.semantic.entities.types.Type;

public class AttributeTypeNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "el atributo %s es de tipo clase %s, que no fue declarado";

    public AttributeTypeNotFoundException(Attribute attribute, Type type) {
        super(attribute.getLexeme(), buildErrorMessage(attribute.getName(), type.getName()));
    }

    private static String buildErrorMessage(String aClass, String type) {
        return String.format(ERROR_MESSAGE, aClass, type);
    }
}
