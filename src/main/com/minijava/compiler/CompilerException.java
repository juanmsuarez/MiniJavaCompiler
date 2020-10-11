package com.minijava.compiler;

public abstract class CompilerException extends Exception {
    private static final String ERROR_DESC = "Error %s en línea %d: %s\n";
    private static final String ERROR_CODE = "[Error:%s|%d]\n";
    private static final String DETAIL_PREFIX = "Detalle: ";
    private static final String DETAIL_LINE = DETAIL_PREFIX + "%s\n";

    private String errorType;
    private int lineNumber;
    private String errorMessage;
    private String lexeme;
    private String line;
    private int lexemePosition;

    public CompilerException(String errorType, int lineNumber, String errorMessage, String lexeme, String line,
                             int lexemePosition) {
        this.errorType = errorType;
        this.lineNumber = lineNumber;
        this.lexeme = lexeme;
        this.errorMessage = errorMessage;
        this.line = line;
        this.lexemePosition = lexemePosition;
    }

    private String buildLexemeIndicator() {
        String detailLineBeforeLexeme = DETAIL_PREFIX + line.substring(0, lexemePosition);
        String spacesBeforeLexeme = detailLineBeforeLexeme.replaceAll("[^\t ]", " ");

        return spacesBeforeLexeme + "^\n";
    }

    @Override
    public String toString() {
        return String.format(ERROR_DESC, errorType, lineNumber + 1, errorMessage)
                + String.format(DETAIL_LINE, line)
                + buildLexemeIndicator()
                + String.format(ERROR_CODE, lexeme, lineNumber + 1);
    }
}
