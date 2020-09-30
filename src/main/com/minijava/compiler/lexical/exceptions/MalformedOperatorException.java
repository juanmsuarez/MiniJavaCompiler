package com.minijava.compiler.lexical.exceptions;

public class MalformedOperatorException extends LexicalException {
    private static final String ERROR_MESSAGE = "%s no es un operador válido.";

    public MalformedOperatorException(int lineNumber, String lexeme, String line, int lexemePosition) {
        super(lineNumber, String.format(ERROR_MESSAGE, lexeme), lexeme, line, lexemePosition);
    }
}
