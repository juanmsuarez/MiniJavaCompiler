package com.minijava.compiler.lexical.exceptions;

import com.minijava.compiler.CompilerException;

public abstract class LexicalException extends CompilerException {
    private static final String ERROR_TYPE = "léxico";
    private static final String DETAIL_PREFIX = "Detalle: ";
    private static final String DETAIL_LINE = DETAIL_PREFIX + "%s\n";

    private String line;
    private int lexemePosition;

    public LexicalException(int lineNumber, String errorMessage, String lexeme, String line, int lexemePosition) {
        super(ERROR_TYPE, lineNumber, errorMessage, lexeme);
        this.line = line;
        this.lexemePosition = lexemePosition;
    }

    private String buildLexemeIndicator() {
        String detailLineBeforeLexeme = DETAIL_PREFIX + line.substring(0, lexemePosition);
        String spacesBeforeLexeme = detailLineBeforeLexeme.replaceAll("[^\t ]", " ");

        return spacesBeforeLexeme + "^\n";
    }

    @Override
    protected String detailString() {
        return String.format(DETAIL_LINE, line)
                + buildLexemeIndicator();
    }
}
