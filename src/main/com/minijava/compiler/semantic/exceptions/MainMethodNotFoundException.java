package com.minijava.compiler.semantic.exceptions;

import com.minijava.compiler.lexical.analyzer.Lexeme;

import static com.minijava.compiler.semantic.entities.PredefinedEntities.MAIN;

public class MainMethodNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "no se encontró el método main (static, void y sin parámetros)";

    public MainMethodNotFoundException() {
        super(new Lexeme(MAIN.getName(), -1, "", 0), ERROR_MESSAGE);
    }
}
