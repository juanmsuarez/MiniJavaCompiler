package com.minijava.compiler.lexical.exceptions;

public abstract class LexicalException extends Exception {
    private static final String ERROR_DESC = "Error léxico en línea %d: %s\n";
    private static final String ERROR_CODE = "[Error:%s|%d]\n";
    private static final String DETAIL_PREFIX = "Detalle: ";
    private static final String DETAIL_LINE = DETAIL_PREFIX + "%s\n";

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

    private String buildLexemeIndicator() {
        String detailLineBeforeLexeme = DETAIL_PREFIX + line.substring(0, lexemePosition);
        String spacesBeforeLexeme = detailLineBeforeLexeme.replaceAll("[^\t ]", " ");

        return spacesBeforeLexeme + "^\n";
    }

    @Override
    public String toString() {
        return String.format(ERROR_DESC, lineNumber + 1, errorMessage)
                + String.format(DETAIL_LINE, line)
                + buildLexemeIndicator()
                + String.format(ERROR_CODE, lexeme, lineNumber + 1);
    }
}
