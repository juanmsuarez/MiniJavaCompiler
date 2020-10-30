package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Parameter;
import com.minijava.compiler.semantic.entities.types.Type;

public class ParameterTypeNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "el parámetro %s es de tipo clase %s, que no fue declarado";

    public ParameterTypeNotFoundException(Parameter parameter, Type type) {
        super(parameter.getLexeme(), buildErrorMessage(parameter.getName(), type.getName()));
    }

    private static String buildErrorMessage(String aClass, String type) {
        return String.format(ERROR_MESSAGE, aClass, type);
    }
}
