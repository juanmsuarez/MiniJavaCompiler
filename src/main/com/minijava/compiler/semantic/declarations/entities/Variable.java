package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.semantic.declarations.entities.types.Type;

public abstract class Variable {
    protected Type type;
    protected String name;

    protected int offset = -1;

    public Variable(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public abstract boolean isInstanceMember();
}
