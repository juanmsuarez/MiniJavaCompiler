package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class ArgumentsNumberMismatchException extends SemanticException {
    private static final String ERROR_MESSAGE = "la llamada a %s debe tener %d argumentos";

    public ArgumentsNumberMismatchException(Lexeme lexeme, int expectedNumber) {
        super(lexeme, buildErrorMessage(lexeme, expectedNumber));
    }

    private static String buildErrorMessage(Lexeme lexeme, int expectedNumber) {
        return String.format(ERROR_MESSAGE, lexeme, expectedNumber);
    }
}
