package com.minijava.compiler.semantic.declarations.exceptions;

import com.minijava.compiler.semantic.declarations.entities.Class;

public class InterfaceNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "la clase %s implementa la interfaz %s, que no fue declarada";

    public InterfaceNotFoundException(Class aClass, String interfaceName) {
        super(aClass.getLexeme(), buildErrorMessage(aClass.getName(), interfaceName));
    }

    private static String buildErrorMessage(String aClass, String parent) {
        return String.format(ERROR_MESSAGE, aClass, parent);
    }
}
