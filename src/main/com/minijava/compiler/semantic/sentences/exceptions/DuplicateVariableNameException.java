package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class DuplicateVariableNameException extends SemanticException {
    private static final String ERROR_MESSAGE = "el nombre de variable %s ya fue usado para un parámetro o una variable declarada anteriormente";

    public DuplicateVariableNameException(Lexeme variableName) {
        super(variableName, buildErrorMessage(variableName.getString()));
    }

    private static String buildErrorMessage(String name) {
        return String.format(ERROR_MESSAGE, name);
    }
}
