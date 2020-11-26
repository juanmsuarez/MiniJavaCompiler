package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class OperatorExpectedConformingTypesException extends SemanticException {
    private static final String ERROR_MESSAGE = "el operador %s solo acepta operandos de tipos conformantes (y no acepta tipos void)";

    public OperatorExpectedConformingTypesException(Lexeme operator) {
        super(operator, buildErrorMessage(operator.getString()));
    }

    private static String buildErrorMessage(String operator) {
        return String.format(ERROR_MESSAGE, operator);
    }
}
