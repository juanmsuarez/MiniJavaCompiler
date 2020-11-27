package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class InvalidReturnTypeException extends SemanticException {
    private static final String ERROR_MESSAGE = "expresión retornada inválida, verifique que el método no tenga tipo de retorno void y que la expresión retornada sea un subtipo";

    public InvalidReturnTypeException(Lexeme returnLexeme) {
        super(returnLexeme, ERROR_MESSAGE);
    }
}
