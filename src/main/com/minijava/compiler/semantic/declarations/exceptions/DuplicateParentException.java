package com.minijava.compiler.semantic.declarations.exceptions;

import com.minijava.compiler.semantic.declarations.entities.Interface;

public class DuplicateParentException extends SemanticException {
    private static final String ERROR_MESSAGE = "la interfaz %s extiende más de una vez a la interfaz %s";

    public DuplicateParentException(Interface anInterface, String interfaceName) {
        super(anInterface.getLexeme(), buildErrorMessage(anInterface.getName(), interfaceName));
    }

    private static String buildErrorMessage(String className, String interfaceName) {
        return String.format(ERROR_MESSAGE, className, interfaceName);
    }
}
