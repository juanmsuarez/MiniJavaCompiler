package com.minijava.compiler.semantic.sentences.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;

public class InvalidCompositeAssignmentException extends SemanticException {
    private static final String ERROR_MESSAGE = "asignación compuesta %s inválida, verifique que el lado izquierdo y el lado derecho sean de tipo entero";

    public InvalidCompositeAssignmentException(Lexeme assignment) {
        super(assignment, buildErrorMessage(assignment.getString()));
    }

    private static String buildErrorMessage(String assignment) {
        return String.format(ERROR_MESSAGE, assignment);
    }
}
