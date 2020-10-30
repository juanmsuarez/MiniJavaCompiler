package com.minijava.compiler.semantic.entities.types;

public class VoidType extends Type {
    private static final String VOID = "void";

    public VoidType() {
        super(VOID);
    }

    public boolean isDefined() {
        return true;
    }
}
