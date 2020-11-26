package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class NonConformingArgumentException extends SemanticException {
    private static final String ERROR_MESSAGE = "el argumento %d comenzado con %s no conforma con el tipo %s";

    public NonConformingArgumentException(Lexeme leftLexeme, int position, Type expectedType) {
        super(leftLexeme, buildErrorMessage(leftLexeme, position, expectedType));
    }

    private static String buildErrorMessage(Lexeme leftLexeme, int position, Type expectedType) {
        return String.format(ERROR_MESSAGE, position + 1, leftLexeme, expectedType.getName());
    }
}
