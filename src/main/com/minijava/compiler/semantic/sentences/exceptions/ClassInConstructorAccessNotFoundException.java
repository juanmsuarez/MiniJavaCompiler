package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class ClassInConstructorAccessNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "no se puedo acceder al constructor %s (clase no declarada)";

    public ClassInConstructorAccessNotFoundException(Lexeme classLexeme) {
        super(classLexeme, buildErrorMessage(classLexeme.getString()));
    }

    private static String buildErrorMessage(String className) {
        return String.format(ERROR_MESSAGE, className);
    }
}
