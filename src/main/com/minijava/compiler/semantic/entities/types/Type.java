package com.minijava.compiler.semantic.entities.types;

public abstract class Type {
    protected String name;

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public abstract boolean isDefined();
}
