package com.minijava.compiler.semantic.sentences.asts;

public class Call extends Sentence {
    private Access access;

    public Call(Access access) {
        super(access.lexeme); // TODO: ante errores, debería ser lexeme = access.getLastInChain() o basta con el primero?
        this.access = access;
    }

    @Override
    public String toString() {
        return "\nCall{" +
                "access=" + access +
                '}';
    }
}
