package com.minijava.compiler.lexicalanalyzer.exceptions;

public class UnclosedCommentException extends LexicalException {
    public UnclosedCommentException(String lexeme, int lineNumber) {
        super(lexeme, lineNumber);
    }

    @Override
    public String toString() {
        return String.format("Error léxico en línea %d: comentario sin cerrar %s \n", lineNumber, lexeme)
                + super.toString();
    }
}
