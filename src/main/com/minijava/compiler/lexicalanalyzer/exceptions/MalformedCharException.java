package com.minijava.compiler.lexicalanalyzer.exceptions;

public class MalformedCharException extends LexicalException {
    private static final String ERROR_MESSAGE = "%s no se encontró un carácter válido luego de la apertura del literal.";

    public MalformedCharException(int lineNumber, String lexeme, String line, int lexemePosition) {
        super(lineNumber, String.format(ERROR_MESSAGE, lexeme), lexeme, line, lexemePosition);
    }
}
