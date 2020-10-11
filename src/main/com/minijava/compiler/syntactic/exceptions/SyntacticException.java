package com.minijava.compiler.syntactic.exceptions;

import com.minijava.compiler.CompilerException;
import com.minijava.compiler.lexical.models.Token;

public class SyntacticException extends CompilerException {
    private static final String ERROR_TYPE = "sintáctico";
    private static final String ERROR_MESSAGE = "se esperaba un %s pero se encontró \"%s\"";

    public SyntacticException(Token foundToken, String expectedTokenName, String line, int lexemePosition) {
        super(ERROR_TYPE, foundToken.getLineNumber(), String.format(ERROR_MESSAGE, expectedTokenName, foundToken.getLexeme()),
                foundToken.getLexeme(), line, lexemePosition);
    }
}
