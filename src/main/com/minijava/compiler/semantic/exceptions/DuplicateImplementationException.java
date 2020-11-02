package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Class;

public class DuplicateImplementationException extends SemanticException {
    private static final String ERROR_MESSAGE = "la clase %s implementa más de una vez a la interfaz %s";

    public DuplicateImplementationException(Class aClass, String interfaceName) {
        super(aClass.getLexeme(), buildErrorMessage(aClass.getName(), interfaceName));
    }

    private static String buildErrorMessage(String className, String interfaceName) {
        return String.format(ERROR_MESSAGE, className, interfaceName);
    }
}
