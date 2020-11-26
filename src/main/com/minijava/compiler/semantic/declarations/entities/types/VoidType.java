package com.minijava.compiler.semantic.declarations.entities.types;

public class VoidType extends Type {
    public static final String VOID = "void";

    public VoidType() {
        super(VOID);
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
        return false;
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
