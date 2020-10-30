package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.semantic.entities.Class;

public class CyclicInheritanceException extends SemanticException {
    private static final String ERROR_MESSAGE = "herencia cíclica involucrando al ancestro %s de la clase %s";

    public CyclicInheritanceException(Class ancestor, Class aClass) {
        super(aClass.getLexeme(), buildErrorMessage(ancestor.getName(), aClass.getName()));
    }

    private static String buildErrorMessage(String ancestorName, String className) {
        return String.format(ERROR_MESSAGE, ancestorName, className);
    }
}