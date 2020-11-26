package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class InvalidChainedMethodAccessException extends SemanticException {
    private static final String ERROR_MESSAGE = "no se puede acceder al método %s (el tipo de la izquierda debe ser una clase, y el método debe ser estar definido en esa clase)";

    public InvalidChainedMethodAccessException(Lexeme access) {
        super(access, buildErrorMessage(access.getString()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
