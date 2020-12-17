package com.minijava.compiler.semantic.sentences.models.entities;

import com.minijava.compiler.semantic.declarations.entities.Variable;
import com.minijava.compiler.semantic.declarations.entities.types.Type;

public class LocalVariable extends Variable {
    public LocalVariable(Type type, String name) {
        super(type, name);
    }

    @Override
    public boolean isInstanceAttribute() {
        return false;
    }
}
