package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Class;

public class ParentNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "la clase %s hereda de la clase %s, que no fue declarada";

    public ParentNotFoundException(Class aClass, String parent) {
        super(aClass.getLexeme(), buildErrorMessage(aClass.getName(), parent));
    }

    private static String buildErrorMessage(String aClass, String parent) {
        return String.format(ERROR_MESSAGE, aClass, parent);
    }
}
