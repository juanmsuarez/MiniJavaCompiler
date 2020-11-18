package com.minijava.compiler.semantic.declarations.entities.types;

public abstract class PrimitiveType extends Type {
    public PrimitiveType(String name) {
        super(name);
    }

    public boolean isValid() {
        return true;
    }

    @Override
    public Type instantiate(String newType) {
        return this;
    }
}
