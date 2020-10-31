package com.minijava.compiler.semantic.exceptions;

public class MainMethodNotFoundException extends SemanticException {
    private static final String ERROR_MESSAGE = "no se encontró el método main (static, void y sin parámetros)";

    public MainMethodNotFoundException() { // TODO: qué código de error mostrar?
        super(ERROR_MESSAGE);
    }
}
