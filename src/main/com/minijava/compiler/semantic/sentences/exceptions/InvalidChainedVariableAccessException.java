package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class InvalidChainedVariableAccessException extends SemanticException {
    private static final String ERROR_MESSAGE = "no se puede acceder a la variable %s (el tipo de la izquierda debe ser una clase, y la variable debe ser un atributo público de esa clase)";

    public InvalidChainedVariableAccessException(Lexeme access) {
        super(access, buildErrorMessage(access.getString()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
