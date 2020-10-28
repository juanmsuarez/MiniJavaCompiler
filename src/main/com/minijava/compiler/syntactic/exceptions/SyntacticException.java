package com.minijava.compiler.syntactic.exceptions;

import com.minijava.compiler.CompilerException;
import com.minijava.compiler.lexical.models.Token;

public class SyntacticException extends CompilerException {
    private static final String ERROR_TYPE = "sintáctico";
    private static final String ERROR_MESSAGE = "se esperaba %s pero se encontró %s";

    private String expectedTokenName;

    public SyntacticException(Token foundToken, String expectedTokenName) {
        super(ERROR_TYPE, foundToken.getLexeme(), buildErrorMessage(expectedTokenName, foundToken));

        this.expectedTokenName = expectedTokenName;
    }

    private static String buildErrorMessage(String expectedTokenName, Token foundToken) {
        return String.format(ERROR_MESSAGE,
                ErrorMessages.TOKEN_DESCRIPTION.getOrDefault(expectedTokenName, expectedTokenName),
                ErrorMessages.LEXEME_DESCRIPTION.getOrDefault(foundToken.getName(), "\"" + foundToken.getLexeme() + "\""));
    }

    public String getExpectedTokenName() {
        return expectedTokenName;
    }
}
