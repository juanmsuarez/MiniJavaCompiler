package com.minijava.compiler.syntactic.exceptions;

import com.minijava.compiler.CompilerException;
import com.minijava.compiler.lexical.models.Token;

public class SyntacticException extends CompilerException {
    private static final String ERROR_TYPE = "sintáctico";
    private static final String ERROR_MESSAGE = "se esperaba %s pero se encontró %s";

    public SyntacticException(Token foundToken, String expectedTokenName, String line, int lexemePosition) {
        super(ERROR_TYPE, foundToken.getLineNumber(), buildErrorMessage(expectedTokenName, foundToken),
                foundToken.getLexeme(), line, lexemePosition);
    }

    private static String buildErrorMessage(String expectedTokenName, Token foundToken) {
        return String.format(ERROR_MESSAGE,
                ErrorMessages.TOKEN_DESCRIPTION.getOrDefault(expectedTokenName, expectedTokenName),
                ErrorMessages.LEXEME_DESCRIPTION.getOrDefault(foundToken.getName(), "\"" + foundToken.getLexeme() + "\""));
    }
}
