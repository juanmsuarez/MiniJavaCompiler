package com.minijava.compiler.semantic.declarations.exceptions;

import com.minijava.compiler.semantic.declarations.entities.Method;
import com.minijava.compiler.semantic.declarations.entities.Unit;

public class InterfaceCollisionException extends SemanticException {
    private static final String ERROR_MESSAGE = "la clase o interfaz %s intenta implementar o heredar dos interfaces que colisionan en el método %s";

    public InterfaceCollisionException(Unit unit, Method method) {
        super(unit.getLexeme(), buildErrorMessage(unit.getName(), method.getName()));
    }

    private static String buildErrorMessage(String unitName, String methodName) {
        return String.format(ERROR_MESSAGE, unitName, methodName);
    }
}
