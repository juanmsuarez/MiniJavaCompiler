package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Parameter;

public class DuplicatedParameterException extends SemanticException {
    private static final String ERROR_MESSAGE = "parámetro %s duplicado";

    public DuplicatedParameterException(Parameter parameter) {
        super(parameter.getLexeme(), buildErrorMessage(parameter.getName()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
