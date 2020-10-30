package com.minijava.compiler.semantic.entities.types;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class ReferenceType extends Type {
    public ReferenceType(String name) {
        super(name);
    }

    public boolean isDefined() {
        return symbolTable.contains(name);
    }
}
