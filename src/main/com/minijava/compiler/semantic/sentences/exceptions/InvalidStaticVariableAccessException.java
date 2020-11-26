package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class InvalidStaticVariableAccessException extends SemanticException {
    private static final String ERROR_MESSAGE = "no se puede acceder a la variable %s (debe estar definida con visibilidad pública y de forma estática en la clase de la izquierda)";

    public InvalidStaticVariableAccessException(Lexeme access) {
        super(access, buildErrorMessage(access.getString()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
