package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class InvalidStaticMethodAccessException extends SemanticException {
    private static final String ERROR_MESSAGE = "no se puede acceder al método %s (debe ser estar definido de forma estática en la clase de la izquierda)";

    public InvalidStaticMethodAccessException(Lexeme access) {
        super(access, buildErrorMessage(access.getString()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
