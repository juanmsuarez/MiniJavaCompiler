package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class VariableNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "no se pudo encontrar la variable %s en este contexto (debe ser una variable local, un parámetro o un atributo visible)";

    public VariableNotFoundException(Lexeme variableAccess) {
        super(variableAccess, buildErrorMessage(variableAccess.getString()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
