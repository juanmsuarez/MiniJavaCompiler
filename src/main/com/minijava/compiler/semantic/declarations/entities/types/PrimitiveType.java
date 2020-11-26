package com.minijava.compiler.semantic.declarations.entities.types;

public abstract class PrimitiveType extends Type {
    public PrimitiveType(String name) {
        super(name);
    }

    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isSubtype(Type type) {
        return type.isSupertype(this);
    }

    @Override
    protected boolean isSupertype(PrimitiveType other) {
        return other.name.equals(name);
    }

    @Override
    protected boolean isSupertype(VoidType other) {
        return false;
    }

    @Override
    protected boolean isSupertype(ReferenceType other) {
        return false;
    }

    @Override
    public Type instantiate(String newType) {
        return this;
    }
}
