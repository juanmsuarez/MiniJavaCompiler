package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class MethodNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "no se pudo encontrar el método %s en este contexto (debe ser un método declarado en la clase o heredado de sus ancestros)";

    public MethodNotFoundException(Lexeme methodAccess) {
        super(methodAccess, buildErrorMessage(methodAccess.getString()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
