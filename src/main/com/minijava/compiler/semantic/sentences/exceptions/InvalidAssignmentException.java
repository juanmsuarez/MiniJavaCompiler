package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class InvalidAssignmentException extends SemanticException {
    private static final String ERROR_MESSAGE = "asignación %s inválida, verifique que el lado izquierdo sea asignable y que el lado derecho sea subtipo del lado izquierdo";

    public InvalidAssignmentException(Lexeme assignment) {
        super(assignment, buildErrorMessage(assignment.getString()));
    }

    private static String buildErrorMessage(String assignment) {
        return String.format(ERROR_MESSAGE, assignment);
    }
}
