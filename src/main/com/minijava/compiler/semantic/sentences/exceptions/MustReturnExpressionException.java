package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class MustReturnExpressionException extends SemanticException {
    private static final String ERROR_MESSAGE = "debe retornar una expresión en métodos con tipo de retorno no void";

    public MustReturnExpressionException(Lexeme returnLexeme) {
        super(returnLexeme, ERROR_MESSAGE);
    }
}
