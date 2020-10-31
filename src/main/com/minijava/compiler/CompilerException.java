package com.minijava.compiler;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public abstract class CompilerException extends Exception {
    private static final String ERROR_DESC = "Error %s en línea %d: %s\n";
    private static final String SHORT_ERROR_DESC = "Error %s: %s\n";
    private static final String ERROR_CODE = "[Error:%s|%d]\n";
    private static final String DETAIL_PREFIX = "Detalle: ";
    private static final String DETAIL_LINE = DETAIL_PREFIX + "%s\n";

    private String errorType;
    private String errorMessage;
    private Lexeme lexeme;

    public CompilerException(String errorType, String errorMessage) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    public CompilerException(String errorType, Lexeme lexeme, String errorMessage) {
        this(errorType, errorMessage);
        this.lexeme = lexeme;
    }

    private String buildLexemeIndicator() {
        String detailLineBeforeLexeme = DETAIL_PREFIX + lexeme.getLine().substring(0, lexeme.getLexemePosition());
        String spacesBeforeLexeme = detailLineBeforeLexeme.replaceAll("[^\t ]", " ");

        return spacesBeforeLexeme + "^\n";
    }

    @Override
    public String toString() {
        if (lexeme == null) {
            return String.format(SHORT_ERROR_DESC, errorType, errorMessage);
        }

        return String.format(ERROR_DESC, errorType, lexeme.getLineNumber() + 1, errorMessage)
                + String.format(DETAIL_LINE, lexeme.getLine())
                + buildLexemeIndicator()
                + String.format(ERROR_CODE, lexeme, lexeme.getLineNumber() + 1);
    }
}
