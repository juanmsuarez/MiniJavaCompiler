package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class ClassInStaticAccessNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "no se puede realizar un acceso estático en la clase %s (no fue declarada)";

    public ClassInStaticAccessNotFoundException(Lexeme classLexeme) {
        super(classLexeme, buildErrorMessage(classLexeme.getString()));
    }

    private static String buildErrorMessage(String className) {
        return String.format(ERROR_MESSAGE, className);
    }
}
