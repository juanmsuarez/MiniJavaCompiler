package com.minijava.compiler.lexicalanalyzer.exceptions;

import java.util.Collections;

public abstract class LexicalException extends Exception {
    private static final String ERROR_DESC = "Error léxico en línea %d: %s\n";
    private static final String ERROR_CODE = "[Error:%s|%d]\n";
    private static final String DETAIL = "Detalle: ";
    private static final String DETAIL_LINE = DETAIL + "%s\n";

    private String lexeme;
    private int lineNumber;
    private String line;
    private int lexemePosition;
    private String errorMessage;

    public LexicalException(int lineNumber, String errorMessage, String lexeme, String line, int lexemePosition) {
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
        this.line = line;
        this.lexemePosition = lexemePosition;
        this.errorMessage = errorMessage;
    }

    private String spaces(int n) {
        return String.join("", Collections.nCopies(n, " "));
    }

    @Override
    public String toString() {
        return String.format(ERROR_DESC, lineNumber + 1, errorMessage)
                + String.format(ERROR_CODE, lexeme, lineNumber + 1)
                + String.format(DETAIL_LINE, line)
                + spaces(DETAIL.length() + lexemePosition) + "^";
    }
}
