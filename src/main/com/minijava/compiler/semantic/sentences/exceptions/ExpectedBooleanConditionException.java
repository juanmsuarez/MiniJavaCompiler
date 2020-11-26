package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class ExpectedBooleanConditionException extends SemanticException {
    private static final String ERROR_MESSAGE = "la sentencia %s debe recibir una expresión booleana como condición";

    public ExpectedBooleanConditionException(Lexeme sentence) {
        super(sentence, buildErrorMessage(sentence.getString()));
    }

    private static String buildErrorMessage(String sentence) {
        return String.format(ERROR_MESSAGE, sentence);
    }
}
