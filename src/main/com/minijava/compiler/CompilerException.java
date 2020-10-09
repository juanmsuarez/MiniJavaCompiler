package com.minijava.compiler;

public abstract class CompilerException extends Exception {
    private static final String ERROR_DESC = "Error %s en línea %d: %s\n";
    private static final String ERROR_CODE = "[Error:%s|%d]\n";

    private String errorType;
    private int lineNumber;
    private String errorMessage;
    private String lexeme;

    public CompilerException(String errorType, int lineNumber, String errorMessage, String lexeme) {
        this.errorType = errorType;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
        this.errorMessage = errorMessage;
    }

    protected abstract String detailString();

    @Override
    public String toString() {
        return String.format(ERROR_DESC, errorType, lineNumber + 1, errorMessage)
                + detailString()
                + String.format(ERROR_CODE, lexeme, lineNumber + 1);
    }
}
