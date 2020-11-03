package com.minijava.compiler.semantic.entities.types;

public abstract class PrimitiveType extends Type {
    public PrimitiveType(String name) {
        super(name);
    }

    public boolean isValid() {
        return true;
    }
}
