package com.minijava.compiler.semantic.declarations.entities.types;

import java.util.Objects;

public abstract class Type {
    protected String name;

    public Type(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public abstract boolean isValid();

    public abstract Type instantiate(String newType);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return name.equals(type.name);
    }

    public abstract boolean isSubtype(Type type);

    protected abstract boolean isSupertype(PrimitiveType other);

    protected abstract boolean isSupertype(VoidType other);

    protected abstract boolean isSupertype(ReferenceType other);

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
